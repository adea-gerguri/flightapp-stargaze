package user.service;

import airline.mappers.AirlineMapper;
import airline.models.AirlineEntity;
import airline.models.dto.CreateAirlineDTO;
import airline.repository.AirlineRepository;
import io.smallrye.mutiny.Uni;
import io.vertx.ext.auth.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import shared.mongoUtils.DeleteResult;
import shared.mongoUtils.InsertResult;
import user.mappers.UserMapper;
import user.models.UserEntity;
import user.models.dto.CreateUserDto;
import user.models.dto.UserDto;
import user.repository.UserRepository;

import java.util.List;

@ApplicationScoped
public class UserService {
    @Inject
    UserRepository userRepository;

    public Uni<List<UserDto>> listUsers(int skip, int limit) {
        return userRepository.listUsers(skip,limit);
    }

    public Uni<InsertResult> addUser(CreateUserDto userDto) {
        if (!userDto.isValid()) {
            return Uni.createFrom().failure(new BadRequestException("User not valid!"));
        }
        return Uni.createFrom()
                .item(UserMapper.toUser(userDto))
                .flatMap(user -> userRepository.addUser(user));
    }

    public Uni<DeleteResult> deleteUser(String id){
        return userRepository.deleteUser(id);
    }
}
