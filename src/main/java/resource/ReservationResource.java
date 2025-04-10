package resource;

import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import model.dto.ReservationDto;
import model.view.Reservation;
import repository.ReservationRepository;

import java.util.List;

@Path("/reservations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservationResource {
    @Inject
    ReservationRepository reservationRepository;

    @GET
//    @RolesAllowed("admin")
    public Uni<List<ReservationDto>> reservationsList(){
        return reservationRepository.listReservations();
    }

    @POST
    @PermitAll
    public Uni<List<ReservationDto>> addReservation(Reservation reservation){
        return reservationRepository.addReservation(reservation)
                .onItem().ignore().andSwitchTo(this::reservationsList);
    }


}
