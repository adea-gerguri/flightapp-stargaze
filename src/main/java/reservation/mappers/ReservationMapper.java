package reservation.mappers;

import static reservation.service.ReservationService.PRICE_BUSINESS;
import static reservation.service.ReservationService.PRICE_CABIN;
import static reservation.service.ReservationService.PRICE_CHECKED_IN;
import static reservation.service.ReservationService.PRICE_COACH;
import static reservation.service.ReservationService.PRICE_FREE_CARRY_ON;
import static reservation.service.ReservationService.PRICE_TROLLEY;
import static reservation.service.ReservationService.PRICE_VIP;

import baggage.models.enums.BaggageType;
import java.time.LocalDateTime;
import reservation.models.ReservationEntity;
import reservation.models.dto.CreateReservationDto;
import reservation.models.dto.ReservationDto;
import reservation.models.enums.ReservationStatus;
import reservation.models.enums.SeatType;

public class ReservationMapper {
  public static ReservationEntity toReservation(CreateReservationDto reservationDto) {
    if (reservationDto != null) {
      SeatType seatType = reservationDto.getSeatSelection();
      BaggageType baggageType = reservationDto.getBaggage();
      double additionalPrice = calculateAdditionalPrice(seatType, baggageType);
      double totalPrice = reservationDto.getPrice() + additionalPrice;

      ReservationEntity reservation = new ReservationEntity();
      reservation.setReservationDate(LocalDateTime.now());
      reservation.setFlightNumber(reservationDto.getFlightNumber());
      reservation.setUserId(reservationDto.getUserId());
      reservation.setSeatSelection(reservationDto.getSeatSelection());
      reservation.setSpecialAssistance(reservationDto.isSpecialAssistance());
      reservation.setBaggage(reservationDto.getBaggage());
      reservation.setPrice(totalPrice);
      reservation.setReservationStatus(ReservationStatus.RESERVED);
    }
    return null;
  }

  private static double calculateAdditionalPrice(SeatType seatSelection, BaggageType baggageType) {
    double seatPrice = 0.0;
    double baggagePrice = 0.0;

    switch (seatSelection) {
      case COACH:
        seatPrice = PRICE_COACH;
        break;
      case BUSINESS:
        seatPrice = PRICE_BUSINESS;
        break;
      case VIP:
        seatPrice = PRICE_VIP;
        break;
    }

    switch (baggageType) {
      case CABIN:
        baggagePrice = PRICE_CABIN;
        break;
      case FREE_CARRY_ON:
        baggagePrice = PRICE_FREE_CARRY_ON;
        break;
      case TROLLEY:
        baggagePrice = PRICE_TROLLEY;
        break;
      case CHECKED_IN:
        baggagePrice = PRICE_CHECKED_IN;
        break;
    }

    return seatPrice + baggagePrice;
  }

  public static ReservationDto toReservationDto(ReservationEntity reservationEntity) {
    if (reservationEntity != null) {
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
