package model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.view.Baggage;
import model.enums.SeatType;
import model.view.Reservation;
import org.bson.types.ObjectId;

import java.sql.Date;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {
    @NotBlank
    private ObjectId flightId;

    @NotBlank
    private Date reservationDate;

    @NotBlank
    private Map<Integer, SeatType> seatSelection;

    @NotBlank
    private boolean specialAssistance;

    @NotBlank
    private Baggage baggage;

    public ReservationDto(Reservation reservation){
        this.setFlightId(reservation.getFlightId());
        this.setReservationDate(reservation.getReservationDate());
        this.setSeatSelection(reservation.getSeatSelection());
        this.setSpecialAssistance(reservation.isSpecialAssistance());
        this.setBaggage(reservation.getBaggage());
    }
}
