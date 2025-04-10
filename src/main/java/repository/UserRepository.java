package repository;


import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import model.dto.UserDto;

import model.mapper.MapperService;
import model.mapper.UserMapper;
import model.view.User;
import org.bson.Document;

import java.util.List;

@ApplicationScoped
public class UserRepository {

    @Inject
    ReactiveMongoClient mongoClient;

    @Inject
    MapperService mapper;

    @Inject
    UserMapper userMapper;

    public Uni<List<UserDto>> listUsers(){
        return getCollection().find()
                .map(document ->{
                    User user = new User();
                    user.setFirstName(document.getString("firstName"));
                    user.setLastName(document.getString("lastName"));
                    user.setUsername(document.getString("username"));
                    user.setEmail(document.getString("email"));
                    user.setPassword(document.getString("password"));
                    user.setPhoneNumber(document.getString("phoneNumber"));
                    user.setPassportNumber(document.getString("passportNumber"));
                    user.setCity(document.getString("city"));
                    user.setCountry(document.getString("country"));
                    user.setStreet(document.getString("street"));
                    user.setBalance(document.getDouble("balance"));
//                    user.setDateOfBirth(document.getDate("dateOfBirth"));
//                    user.setUserType((UserType) document.get("userType"));
                    return mapper.map(user, UserDto.class);
                }).collect().asList();
    }

    public Uni<Void> addUser(User user)  {
        Document document = new Document()
                .append("firstName", user.getFirstName())
                .append("lastName", user.getLastName())
                .append("username", user.getUsername())
                .append("password", PasswordHasher.generateSaltedHash(user.getPassword(), PasswordHasher.generateSalt()))
                .append("email", user.getEmail())
                .append("city", user.getCity())
                .append("country", user.getCountry())
                .append("street", user.getStreet())
                .append("phoneNumber",user.getPhoneNumber())
                .append("passportNumber", user.getPassportNumber())
                .append("balance", user.getBalance())
                .append("dateOfBirth", user.getDateOfBirth())
                .append("userType", user.getUserType());
        return getCollection().insertOne(document)
                .onItem().ignore().andContinueWithNull();

    }
    private ReactiveMongoCollection<Document> getCollection(){
        return mongoClient.getDatabase("stargaze").getCollection("users");
    }




}
