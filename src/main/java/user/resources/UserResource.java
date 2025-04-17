package user.resources;

import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import shared.PaginationQueryParams;
import shared.mongoUtils.DeleteResult;
import shared.mongoUtils.InsertResult;
import user.models.dto.CreateUserDto;
import user.models.dto.UserDto;
import user.service.UserService;

import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    @Inject
    UserService service;

    @GET
    @PermitAll
    public Uni<List<UserDto>> listUsers(@BeanParam PaginationQueryParams params) {
        return service.listUsers(params.getSkip(), params.getLimit());
    }

    @POST
    @PermitAll
    public Uni<InsertResult> addUser(CreateUserDto createUserDto) {
        return service.addUser(createUserDto);
    }

    @DELETE
    @PermitAll
    @Path("/{id}")
    public Uni<DeleteResult> deleteUser(@PathParam("id") String id) {
        return service.deleteUser(id);
    }
}
