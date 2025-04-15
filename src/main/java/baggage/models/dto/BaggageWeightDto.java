package baggage.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BaggageWeightDto {
    private String baggageType;
    private double totalWeight;
    private int count;
}

