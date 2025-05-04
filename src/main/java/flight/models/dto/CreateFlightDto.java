package flight.models.dto;

import flight.models.enums.FlightStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateFlightDto {
    @NotNull private String flightNumber;
    @NotNull private String departureAirportId;
    @NotNull private String arrivalAirportId;
    @NotNull private LocalDateTime departureTime;
    @NotNull private LocalDateTime arrivalTime;
    @NotNull private FlightStatus status;
    @NotNull private double price;
    @NotNull private String airline;
    @NotNull private Integer capacity;
    @NotNull private boolean booked;
}
