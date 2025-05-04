package reservation.models;


import baggage.models.BaggageEntity;
import baggage.models.enums.BaggageType;
import lombok.*;
import reservation.models.enums.ReservationStatus;
import reservation.models.enums.SeatType;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEntity {
    private String id;
    private String userId;
    private String flightNumber;
    private LocalDateTime reservationDate;
    private ReservationStatus reservationStatus;
    private SeatType seatSelection;
    private boolean specialAssistance;
    private double price;
    private BaggageType baggage;
}
