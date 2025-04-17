package flight.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import flight.enums.FlightType;

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
    private String departureTime;
    private String arrivalTime;
    private FlightType flightType;
    private String status;
    private double price;
    private String airline;
    private Integer capacity;
    private Boolean booked;
}
