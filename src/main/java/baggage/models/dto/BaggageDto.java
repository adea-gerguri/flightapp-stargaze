package baggage.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import baggage.enums.BaggageType;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaggageDto {
    private String reservationId;
    private BaggageType baggageType;
    private double weight;
    private double price;
}
