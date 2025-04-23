package flight.models.dto;

import io.quarkus.arc.All;
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
public class FlightInfoDto {
    @NotNull private String departureAirportId;
    @NotNull private String arrivalAirportId;
    @NotNull private LocalDateTime departureTime;
    @NotNull private LocalDateTime arrivalTime;
    @NotNull private Double price;
    private FlightInfoDto outboundFlight;
    private FlightInfoDto returnFlight;
    private double totalPrice;
    private long totalTravelTime;
    private long totalWaitingTime;
}
