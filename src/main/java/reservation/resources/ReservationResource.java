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

}
