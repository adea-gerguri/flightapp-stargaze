package baggage.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import baggage.models.enums.BaggageType;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaggageEntity {
    private String id;
    private String reservationId;
    private BaggageType baggageType;
    private double weight;
    private double price;
}
