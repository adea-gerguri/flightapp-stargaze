package reservation.models.dto;

import baggage.models.BaggageEntity;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import reservation.enums.ReservationStatus;
import reservation.enums.SeatType;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateReservationDto {
    private LocalDateTime reservationDate;
    private String flightNumber;
    private String userId;
    private SeatType seatSelection;
    private ReservationStatus reservationStatus;
    private boolean specialAssistance;
    private double price;
    private BaggageEntity baggage;
}
