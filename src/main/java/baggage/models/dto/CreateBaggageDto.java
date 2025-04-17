package baggage.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import baggage.enums.BaggageType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateBaggageDto {
    private BaggageType baggageType;
    private double length;
    private double height;
    private double width;
    private double weight;
    private double price;
}