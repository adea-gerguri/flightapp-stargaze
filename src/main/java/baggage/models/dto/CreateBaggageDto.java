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
    @NotBlank
    private BaggageType baggageType;

    @NotBlank
    private double length;

    @NotBlank
    private double height;

    @NotBlank
    private double width;

    @NotBlank
    private double weight;

    @NotBlank
    private double price;

    public boolean isValid() {
        return length != 0 && height != 0 && weight != 0 && width != 0 && price != 0 && baggageType != null;
    }
}