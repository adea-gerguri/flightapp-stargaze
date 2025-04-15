package baggage.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaggagePriceDto {
    private String reservationId;
    private double totalPrice;
    private int count;
}
