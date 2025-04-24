package reservation.resources;

import reservation.models.dto.RevenueAggregationDto;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import reservation.models.dto.RevenueQueryParams;
import reservation.models.dto.*;
import reservation.models.ReservationEntity;
import reservation.service.ReservationService;
import shared.PaginationQueryParams;
import shared.mongoUtils.InsertResult;

import java.util.List;

@Path("/reservations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservationResource {
    @Inject
    ReservationService service;

    @GET
    @PermitAll
    public Uni<List<ReservationEntity>> listReservations(@BeanParam PaginationQueryParams paginationQueryParams) {
        return service.listReservations(paginationQueryParams);
    }

    @POST
    @PermitAll
    public Uni<InsertResult> createReservation(CreateReservationDto createReservationDto) {
        return service.createReservation(createReservationDto);
    }

    @GET
    @Path("/find")
    public Uni<ReservationDto> findByUserIdAndFlightNumber(
            @QueryParam("userId") String userId,
            @QueryParam("flightNumber") String flightNumber) {
        return service.findByUserIdAndFlightNumber(userId, flightNumber);
    }

    @GET
    @Path("/user-most-refunded-tickets")
    @RolesAllowed("Admin")
    public Uni<List<UserReservationDto>> findUserWithMostRefundedTickets() {
        return service.findUserWithMostRefundedTickets();
    }

    @GET
    @Path("/user-most-reserved-tickets")
    @RolesAllowed("Admin")
    public Uni<List<UserReservationDto>> findUserWithMostReservedTickets(){
        return service.findUserWithMostReservedTickets();
    }

    @GET
    @PermitAll
    @Path("/revenue-over-time")
    public Uni<List<ReservationRevenueDto>> salesOverTime(@BeanParam RevenueQueryParams revenueQueryParams){
        return service.salesOverTime(revenueQueryParams);
    }

    @GET
    @PermitAll
    @Path("/faceted-revenue")
    public Uni<List<ReservationRevenueDto>> facetedRevenue(@BeanParam RevenueQueryParams revenueQueryParams){
        return service.facetedRevenue(revenueQueryParams);
    }

    @GET
    @PermitAll
    @Path("/revenue-over-timeperiods")
    public Uni<List<RevenueAggregationDto>> revenueOverTimePeriods(@BeanParam RevenueQueryParams revenueQueryParams){
        return service.revenueOverTime(revenueQueryParams);
    }

}
