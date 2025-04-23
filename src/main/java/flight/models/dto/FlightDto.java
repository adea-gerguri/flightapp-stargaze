package flight.models.dto;

import flight.enums.FlightStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import flight.enums.FlightType;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightDto {
    private List<StopoverDto> fullPath;
    private long totalTravelTime;
    private long totalWaitingTime;
    @NotNull private String flightNumber;
    @NotNull private LocalDateTime departureTime;
    @NotNull private LocalDateTime arrivalTime;
    @NotNull private FlightType flightType;
    @NotNull private String departureAirportId;
    @NotNull private String arrivalAirportId;
    @NotNull private FlightStatus status;
    @NotNull private double price;
    @NotNull private String airline;
    @NotNull private Integer capacity;
    @NotNull private boolean booked;
}
