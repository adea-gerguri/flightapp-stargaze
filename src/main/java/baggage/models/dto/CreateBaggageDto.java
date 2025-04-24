package baggage.models.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import baggage.models.enums.BaggageType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateBaggageDto {
    @NotNull private BaggageType baggageType;
    @NotNull private double weight;
    @NotNull private double price;
}