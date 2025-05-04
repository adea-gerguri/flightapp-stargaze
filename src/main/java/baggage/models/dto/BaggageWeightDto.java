package baggage.models.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BaggageWeightDto {
    @NotNull private String baggageType;
    @NotNull private double totalWeight;
    @NotNull private int count;
}

