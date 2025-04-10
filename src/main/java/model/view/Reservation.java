package model.view;


import io.quarkus.mongodb.MongoClientName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.enums.SeatType;
import org.bson.types.ObjectId;

import java.sql.Date;
import java.util.Map;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@MongoClientName("reservations")
public class Reservation {
    private ObjectId id;
    private ObjectId flightId;
    private Date reservationDate;
    private Map<Integer, SeatType> seatSelection;
    private boolean specialAssistance;
    private Baggage baggage;
}
