package model.mapper;

import jakarta.enterprise.context.RequestScoped;
import model.dto.ReservationDto;
import model.view.Reservation;

@RequestScoped
public class ReservationMapper {
    public Reservation toReservation(ReservationDto reservationDto) {
        if(reservationDto!=null){
            Reservation reservation = new Reservation();
            reservation.setReservationDate(reservationDto.getReservationDate());
            reservation.setBaggage(reservationDto.getBaggage());
            reservation.setSeatSelection(reservationDto.getSeatSelection());
            reservation.setSpecialAssistance(reservationDto.isSpecialAssistance());
            return reservation;
        }
        return null;
    }
    public ReservationDto toReservationDto(Reservation reservation){
        if(reservation!=null){
            ReservationDto reservationDto = new ReservationDto();
            reservationDto.setReservationDate(reservation.getReservationDate());
            reservationDto.setBaggage(reservation.getBaggage());
            reservationDto.setSeatSelection(reservation.getSeatSelection());
            reservationDto.setSpecialAssistance(reservation.isSpecialAssistance());
            return reservationDto;
        }
        return null;
    }
}
