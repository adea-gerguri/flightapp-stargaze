package reservation.models;


import baggage.models.BaggageEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import reservation.enums.SeatType;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEntity {
    private String id;
    private String userId;
    private String flightId;
    private Date reservationDate;
    private Map<Integer, SeatType> seatSelection;
    private boolean specialAssistance;
}
