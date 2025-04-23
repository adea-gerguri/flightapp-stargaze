package flight.resources;


import flight.RouteQueryParams;
import flight.exceptions.FlightException;
import flight.models.dto.*;
import flight.service.FlightService;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import shared.PaginationQueryParams;
import shared.mongoUtils.DeleteResult;
import shared.mongoUtils.InsertResult;

import java.time.LocalDateTime;
import java.util.List;

@Path("/flights")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FlightResource {

    @Inject
    FlightService flightService;

    @GET
    @PermitAll
    @Path("/lowest")
    public Uni<List<FlightDto>> listLowestPrice(@BeanParam PaginationQueryParams params){
        return flightService.listLowestPrice(PaginationQueryParams params);
    }


    @POST
//    @RolesAllowed("admin")
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
    @Path("/fastest")
    public Uni<List<FlightDto>> getFastestRoute(@BeanParam RouteQueryParams routeQueryParams) {
        return flightService.getFastestRoute(routeQueryParams);
    }

    @GET
    @Path("/stopover")
    public Uni<List<FlightDto>> possibleStopOvers(@BeanParam RouteQueryParams params) {
        return flightService.getFastestStopover(params.getDepartureAirportId(), params.getDestinationAirportId(), params.getDepartureDate());
    }

    @GET
    @Path("/cheapest")
    public Uni<List<FlightDto>> getCheapestRoute(@BeanParam RouteQueryParams params) {
        return flightService.getCheapestRoute(params.getDepartureAirportId(), params.getDestinationAirportId(), params.getDepartureDate());
    }

    @GET
    @Path("/most-expensive")
    public Uni<List<FlightDto>> getMostExpensiveRoute(@BeanParam RouteQueryParams params) {
        return flightService.getMostExpensiveRoute(params.getDepartureAirportId(), params.getDestinationAirportId(), params.getDepartureDate());
    }


    @GET
    @Path("/two-way")
    public Uni<List<TwoWayFlightDto>> getTwoWayFlights(
            @QueryParam("departureAirportId") String departureAirportId,
            @QueryParam("destinationAirportId") String destinationAirportId,
            @QueryParam("departureDateTime") String departureDateTimeStr,
            @QueryParam("returnDateTime") String returnDateTimeStr) {

        return flightService.findTwoWayFlights(departureAirportId, destinationAirportId, departureDateTimeStr, returnDateTimeStr);
    }
}

