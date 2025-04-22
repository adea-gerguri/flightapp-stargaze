package reservation.models;


import baggage.models.BaggageEntity;
import lombok.*;
import org.bson.codecs.pojo.annotations.BsonId;
import reservation.enums.ReservationStatus;
import reservation.enums.SeatType;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

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
    private BaggageEntity baggage;
}
