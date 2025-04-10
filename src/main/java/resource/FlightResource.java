package resource;


import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import model.dto.FlightDto;
import model.view.Flight;
import repository.FlightRepository;

import java.util.List;

@Path("/flights")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FlightResource {
    @Inject
    FlightRepository flightRepository;

    @GET
    @PermitAll
    public Uni<List<FlightDto>> listFlights() {
        return flightRepository.listFlights();
    }

    @POST
    @PermitAll
    public Uni<List<FlightDto>> addFlight(Flight flight){
        return flightRepository.addFlight(flight)
                .onItem().ignore().andSwitchTo(this::listFlights);
    }
}
