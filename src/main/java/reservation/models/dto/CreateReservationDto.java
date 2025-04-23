package reservation.models.dto;

import baggage.models.BaggageEntity;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull private LocalDateTime reservationDate;
    @NotNull private String flightNumber;
    @NotNull private String userId;
    @NotNull private SeatType seatSelection;
    @NotNull private ReservationStatus reservationStatus;
    @NotNull private boolean specialAssistance;
    @NotNull private double price;
    @NotNull private BaggageEntity baggage;
}
