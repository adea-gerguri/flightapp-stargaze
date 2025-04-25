package user.service;

import com.mongodb.reactivestreams.client.ClientSession;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import reservation.models.dto.UserRefundDto;
import reservation.service.ReservationService;
import shared.GlobalHibernateValidator;
import shared.PaginationQueryParams;
import shared.mongoUtils.DeleteResult;
import shared.mongoUtils.InsertResult;
import shared.mongoUtils.UpdateResult;
import user.exceptions.UserException;
import user.mappers.UserMapper;
import user.models.dto.CreateUserDto;
import user.models.dto.UserDto;
import user.repository.UserRepository;

import java.util.List;

@ApplicationScoped
public class UserService {
    @Inject
    UserRepository userRepository;

    @Inject
    ReservationService reservationService;

    @Inject
    GlobalHibernateValidator validator;

    public Uni<List<UserDto>> listUsers(PaginationQueryParams paginationQueryParams) {
        return userRepository.listUsers(paginationQueryParams);
    }

    public Uni<InsertResult> addUser(CreateUserDto userDto) {
        return validator.validate(userDto)
                .flatMap(validatedDto->{
                    return userRepository.addUser(UserMapper.toUser(validatedDto));
                });
    }

    public Uni<DeleteResult> deleteUser(String id){
        return userRepository.deleteUser(id);
    }

    public Uni<UpdateResult> increaseBalance(String userId, double price, ClientSession clientSession) {
        return userRepository.increaseBalance(userId, price, clientSession);
    }

    public Uni<Void> processRefundForUser(String userId, String flightNumber) {
        return reservationService.processRefund(userId, flightNumber);
    }

    public Uni<UpdateResult> decreaseBalance(String userId, double amount, ClientSession clientSession) {
        return userRepository.decreaseBalance(userId, amount, clientSession);
    }
}

