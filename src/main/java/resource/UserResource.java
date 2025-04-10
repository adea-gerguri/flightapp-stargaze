package resource;

import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import model.dto.UserDto;
import model.view.User;
import repository.UserRepository;

import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    @Inject
    UserRepository userRepository;

    @GET
    @PermitAll
    public Uni<List<UserDto>> listUsers(){
        return userRepository.listUsers();
    }


    @POST
    @PermitAll
    @Transactional
    public Uni<List<UserDto>> add(User user) {
        return userRepository.addUser(user)
                .onItem().ignore().andSwitchTo(this::listUsers);
    }
}
