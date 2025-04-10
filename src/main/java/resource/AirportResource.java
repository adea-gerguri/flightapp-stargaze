package resource;

import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import model.dto.AirportDto;
import model.view.Airport;
import repository.AirportRepository;

import java.util.List;

@Path("/airports")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AirportResource {
    @Inject
    AirportRepository airportRepository;

    @GET
    @PermitAll
    public Uni<List<AirportDto>> listAirports(){
        return airportRepository.listAirports();
    }

    @POST
//    @RolesAllowed("admin")
    public Uni<List<AirportDto>> addAirport(Airport airport){
        return airportRepository.addAirport(airport)
                .onItem().ignore().andSwitchTo(this::listAirports);
    }

}
