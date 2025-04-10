package resource;

import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import model.dto.AirlineDto;
import model.view.Airline;
import repository.AirlineRepository;

import java.util.List;

@Path("/airlines")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AirlineResource {
    @Inject
    AirlineRepository airlineRepository;

    @GET
    @PermitAll
    public Uni<List<AirlineDto>> listAirlines(){
        return airlineRepository.listAirlines();
    }

    @POST
    @RolesAllowed("admin")
    @Path("/admin")
    public Uni<List<AirlineDto>> addAirline(Airline airline){
        return airlineRepository.addAirline(airline)
                .onItem().ignore().andSwitchTo(this::listAirlines);
    }
}
