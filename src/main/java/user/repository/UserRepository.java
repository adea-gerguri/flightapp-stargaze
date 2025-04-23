package user.repository;

import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Updates;
import com.mongodb.reactivestreams.client.ClientSession;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.conversions.Bson;
import shared.PaginationQueryParams;
import shared.mongoUtils.DeleteResult;
import shared.mongoUtils.InsertResult;
import shared.mongoUtils.MongoUtil;
import shared.mongoUtils.UpdateResult;
import user.exceptions.UserException;
import user.models.dto.UserDto;
import user.models.UserEntity;


import java.util.List;

import static com.mongodb.client.model.Sorts.ascending;

@ApplicationScoped
public class UserRepository {
    @Inject
    MongoUtil mongoService;

    public Uni<List<UserDto>> listUsers(PaginationQueryParams paginationQueryParams) {
        List<Bson> pipeline = List.of(
                Aggregates.sort(ascending("name")),
                Aggregates.skip(paginationQueryParams.getSkip()),
                Aggregates.limit(paginationQueryParams.getLimit()),
                Aggregates.project(Projections.fields(
                        Projections.include("id", "name", "email", "phoneNumber")
                ))
        );

        return MongoUtil.aggregate(getCollection(), pipeline, UserDto.class);
    }


    public Uni<InsertResult> addUser(UserEntity airline) {
       return MongoUtil.insertOne(getCollection(), airline);
    }

    public Uni<DeleteResult> deleteUser(String id) {
        return MongoUtil.deleteOne(getCollection(), id);
    }


    public Uni<UpdateResult> decreaseBalance(String userId, double price, ClientSession clientSession) {
        Bson update = Updates.inc("balance", -price);
        return MongoUtil.updateOne(getCollection(), userId, update, clientSession);
    }

    public Uni<UpdateResult> increaseBalance(String userId, double price, ClientSession clientSession) {
        Bson update = Updates.inc("balance", price);
        return MongoUtil.updateOne(getCollection(), userId, update, clientSession);
    }


    private ReactiveMongoCollection<UserEntity> getCollection() {
        return mongoService.getCollection("users",UserEntity.class);
    }
}
