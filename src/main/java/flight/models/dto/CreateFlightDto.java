package flight.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import flight.enums.FlightType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateFlightDto {
    private String flightNumber;
    private String departureAirportId;
    private String arrivalAirportId;
    private String departureTime;
    private String arrivalTime;
    private String status;
    private double price;
    private String airline;
    private Integer capacity;
    private Boolean booked;
}
