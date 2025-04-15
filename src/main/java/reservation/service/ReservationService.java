package reservation.service;

import airline.mappers.AirlineMapper;
import airline.models.AirlineEntity;
import airline.models.dto.CreateAirlineDTO;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import reservation.mappers.ReservationMapper;
import reservation.models.ReservationEntity;
import reservation.models.dto.CreateReservationDto;
import reservation.repository.ReservationRepository;
import shared.mongoUtils.InsertResult;

import java.util.List;

@ApplicationScoped
public class ReservationService {
    @Inject
    ReservationRepository reservationRepository;

    public Uni<List<ReservationEntity>> listReservations() {
        return reservationRepository.listReservations();
    }

}
