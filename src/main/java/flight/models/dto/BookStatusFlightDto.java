package flight.models.dto;

import flight.enums.FlightStatus;
import flight.enums.FlightType;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class BookStatusFlightDto {
    private String flightNumber;
    private Integer capacity;
    private boolean booked;
}
