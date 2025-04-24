package flight.models.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class BookStatusFlightDto {
    @NotNull private String flightNumber;
    @NotNull private int capacity;
    @NotNull private boolean booked;
}
