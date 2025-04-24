package reservation.models.dto;

import baggage.models.BaggageEntity;
import baggage.models.enums.BaggageType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import reservation.models.enums.ReservationStatus;
import reservation.models.enums.SeatType;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateReservationDto {
    private LocalDateTime reservationDate;
    @NotNull private String flightNumber;
    @NotNull private String userId;
    @NotNull private SeatType seatSelection;
    private ReservationStatus reservationStatus;
    @NotNull private boolean specialAssistance;
    private double price;
    private double totalPrice;
    @NotNull private BaggageType baggage;
}
