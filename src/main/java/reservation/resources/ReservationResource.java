package reservation.resources;

import airline.models.AirlineEntity;
import airline.models.dto.CreateAirlineDTO;
import airline.service.AirlineService;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import reservation.models.dto.CreateReservationDto;
import reservation.models.dto.ReservationDto;
import reservation.models.ReservationEntity;
import reservation.models.dto.UserRefundDto;
import reservation.models.dto.UserReservationDto;
import reservation.repository.ReservationRepository;
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
    public Uni<List<ReservationEntity>> listReservations(@BeanParam PaginationQueryParams params) {
        return service.listReservations(params.getSkip(), params.getLimit());
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
    @Path("/most-reservations")
    @PermitAll
    public Uni<UserReservationDto> getUserWithMostReservations() {
        return service.findUserWithMostReservations();
    }

    @GET
    @Path("/user-most-refunded-tickets")
    public Uni<UserReservationDto> findUserWithMostRefundedTickets() {
        return service.findUserWithMostRefundedTickets();
    }

}
