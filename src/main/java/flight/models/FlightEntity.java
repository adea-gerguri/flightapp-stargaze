package flight.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import flight.models.enums.FlightType;
import flight.models.enums.FlightStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightEntity {
    private String id;
    private String flightNumber;
    private String departureAirportId;
    private String arrivalAirportId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private FlightType flightType;
    private FlightStatus flightStatus;
    private double price;
    private String airline;
    private Integer capacity;
    private boolean booked;

}
