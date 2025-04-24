package flight.models.dto;

import flight.models.enums.FlightStatus;
import flight.models.enums.FlightType;
import jakarta.validation.constraints.NotNull;
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
    @NotNull private String flightNumber;
    @NotNull private LocalDateTime departureTime;
    @NotNull private LocalDateTime arrivalTime;
    @NotNull private FlightType flightType;
    @NotNull private FlightStatus status;
    @NotNull private double price;
    @NotNull private String airline;
    @NotNull private Integer capacity;
    @NotNull private boolean booked;
    private double totalPrice;
    private FlightInfoDto outboundFlight;
    private FlightInfoDto returnFlight;


}

