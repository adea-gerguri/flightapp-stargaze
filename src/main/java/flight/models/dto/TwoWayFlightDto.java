package flight.models.dto;

import flight.enums.FlightType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TwoWayFlightDto {
    private List<StopoverDto> fullPath;
    private Duration totalTravelTime;
    private Duration totalWaitingTime;
    private String flightNumber;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private FlightType flightType;
    private String status;
    private double price;
    private String airline;
    private Integer capacity;
    private boolean booked;
    private double totalPrice;
    private FlightInfoDto outboundFlight;
    private FlightInfoDto returnFlight;


}

