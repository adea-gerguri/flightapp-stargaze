package flight.models.dto;

import jakarta.validation.constraints.NotBlank;
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
    private String flightNumber;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private FlightType flightType;
    private String departureAirportId;
    private String arrivalAirportId;
    private String status;
    private double price;
    private String airline;
    private Integer capacity;
    private boolean booked;
}
