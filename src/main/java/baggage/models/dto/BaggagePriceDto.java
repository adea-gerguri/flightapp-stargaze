package baggage.models.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaggagePriceDto {
    @NotNull private String reservationId;
    @NotNull private double totalPrice;
    @NotNull private int count;
}
