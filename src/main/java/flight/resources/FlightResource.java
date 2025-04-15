package flight.resources;


import flight.models.dto.CreateFlightDto;
import flight.models.dto.FlightDto;
import flight.models.dto.StopoverDto;
import flight.service.FlightService;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import shared.mongoUtils.DeleteResult;
import shared.mongoUtils.InsertResult;

import java.util.List;

@Path("/flights")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FlightResource {

    @Inject
    FlightService flightService;

    @GET
    @PermitAll
    @Path("/skip/{skip}/limit/{limit}/")
    public Uni<List<FlightDto>> listLowestPrice(@PathParam("skip") int skip, @PathParam("limit") int limit){
        return flightService.listLowestPrice(skip, limit);
    }


    @POST
    @RolesAllowed("admin")
    public Uni<InsertResult> addFlight(CreateFlightDto flightDto) {
        return flightService.addFlight(flightDto);
    }

    @DELETE
    @RolesAllowed("admin")
    @Path("/{id}")
    public Uni<DeleteResult> deleteById(@PathParam("id") String id) {
        return flightService.deleteById(id);
    }


    @GET
    @Path("/fastest/{departureAirportId}/{destinationAirportId}/{departureDate}")
    public Uni<List<FlightDto>> getFastestRoute(@PathParam("departureAirportId") String departureAirportId,
                                            @PathParam("destinationAirportId") String destinationAirportId,
                                            @PathParam("departureDate") String departureDate) {
        return flightService.getFastestRoute(departureAirportId, destinationAirportId, departureDate);
    }

    @GET
    @Path("/stopover/{departureAirportId}/{destinationAirportId}/{departureDate}")
    public Uni<List<FlightDto>> possibleStopOvers(@PathParam("departureAirportId") String departureAirportId,
                                                          @PathParam("destinationAirportId") String destinationAirportId,
                                                          @PathParam("departureDate") String departureDate) {
        return flightService.getFastestStopover(departureAirportId, destinationAirportId, departureDate);
    }


    @GET
    @Path("/cheapest/{departureAirportId}/{destinationAirportId}/{departureDate}")
    public Uni<List<FlightDto>> getCheapestRoute(@PathParam("departureAirportId") String departureAirportId,
                                            @PathParam("destinationAirportId") String destinationAirportId,
                                            @PathParam("departureDate") String departureDate) {
        return flightService.getCheapestRoute(departureAirportId, destinationAirportId, departureDate);
    }
}

