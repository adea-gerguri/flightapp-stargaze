package flight.models.dto;

import io.quarkus.arc.All;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FlightInfoDto {
    private String departureAirportId;
    private String arrivalAirportId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Double price;
    private FlightInfoDto outboundFlight;
    private FlightInfoDto returnFlight;
    private double totalPrice;
    private long totalTravelTime;
    private long totalWaitingTime;
}
