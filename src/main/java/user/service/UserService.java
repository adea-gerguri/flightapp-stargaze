package user.service;

import airline.mappers.AirlineMapper;
import airline.models.AirlineEntity;
import airline.models.dto.CreateAirlineDTO;
import airline.repository.AirlineRepository;
import io.smallrye.mutiny.Uni;
import io.vertx.ext.auth.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.BadRequestException;
import shared.GlobalHibernateValidator;
import shared.mongoUtils.DeleteResult;
import shared.mongoUtils.InsertResult;
import user.exceptions.UserException;
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

    @Inject
    GlobalHibernateValidator validator;

    public Uni<List<UserDto>> listUsers(int skip, int limit) {
        return userRepository.listUsers(skip,limit);
    }

    public Uni<InsertResult> addUser(CreateUserDto userDto) {
        return validator.validate(userDto)
                .onFailure(ConstraintViolationException.class)
                .transform(e->new UserException(e.getMessage(),400))
                .flatMap(validatedDto->{
                    return userRepository.addUser(UserMapper.toUser(validatedDto));
                });
    }

    public Uni<DeleteResult> deleteUser(String id){
        return userRepository.deleteUser(id)
                .onItem()
                .transform(deleteResult->{
                    if(deleteResult.getDeletedCount()==0){
                        throw new UserException("User not found",404);
                    }
                    return deleteResult;
                });
    }
}

