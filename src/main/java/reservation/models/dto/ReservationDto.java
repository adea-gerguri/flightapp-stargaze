package reservation.models.dto;

import baggage.models.enums.BaggageType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import baggage.models.BaggageEntity;
import reservation.models.enums.ReservationStatus;
import reservation.models.enums.SeatType;

import java.time.LocalDateTime;

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
    private BaggageType baggage;
}
