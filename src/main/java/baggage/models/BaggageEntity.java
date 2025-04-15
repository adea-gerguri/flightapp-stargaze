package baggage.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import baggage.enums.BaggageType;
import org.bson.types.ObjectId;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaggageEntity {
    private String id;
    private String reservationId;
    private BaggageType baggageType;
    private double length;
    private double height;
    private double width;
    private double weight;
    private double price;
}
