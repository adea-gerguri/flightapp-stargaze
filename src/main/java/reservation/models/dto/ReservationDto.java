package reservation.models.dto;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import baggage.models.BaggageEntity;
import reservation.enums.ReservationStatus;
import reservation.enums.SeatType;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {
    private String userId;
    private String flightNumber;
    private LocalDateTime reservationDate;
    private SeatType seatSelection;
    private ReservationStatus reservationStatus;
    private boolean specialAssistance;
    private double price;
    private BaggageEntity baggage;
}
