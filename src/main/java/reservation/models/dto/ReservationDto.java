package reservation.models.dto;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull private String userId;
    @NotNull private String flightNumber;
    @NotNull private LocalDateTime reservationDate;
    @NotNull  private SeatType seatSelection;
    @NotNull private ReservationStatus reservationStatus;
    @NotNull private boolean specialAssistance;
    @NotNull private double price;
    private BaggageEntity baggage;
}
