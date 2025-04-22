package reservation.mappers;

import reservation.models.dto.CreateReservationDto;
import reservation.models.dto.ReservationDto;
import reservation.models.ReservationEntity;

public class ReservationMapper {
    public static ReservationEntity toReservation(CreateReservationDto reservationDto) {
        if(reservationDto!=null){
            ReservationEntity reservationEntity = new ReservationEntity();
            reservationEntity.setFlightNumber(reservationDto.getFlightNumber());
            reservationEntity.setReservationDate(reservationDto.getReservationDate());
            reservationEntity.setPrice(reservationDto.getPrice());
            reservationEntity.setSeatSelection(reservationDto.getSeatSelection());
            reservationEntity.setSpecialAssistance(reservationDto.isSpecialAssistance());
            return reservationEntity;
        }
        return null;
    }
    public static ReservationDto toReservationDto(ReservationEntity reservationEntity){
        if(reservationEntity !=null){
            ReservationDto reservationDto = new ReservationDto();
            reservationDto.setReservationDate(reservationEntity.getReservationDate());
            reservationDto.setPrice(reservationEntity.getPrice());
            reservationDto.setSeatSelection(reservationEntity.getSeatSelection());
            reservationDto.setSpecialAssistance(reservationEntity.isSpecialAssistance());
            return reservationDto;
        }
        return null;
    }
}
