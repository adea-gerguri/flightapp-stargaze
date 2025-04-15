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

    @NotBlank
    private String flightNumber;


    @NotBlank
    private String departureTime;

    @NotBlank
    private String arrivalTime;

    @NotBlank
    private FlightType flightType;

    @NotBlank
    private String status;

    @NotBlank
    private double price;

    @NotBlank
    private String airline;

    @NotBlank
    private Integer capacity;

    @NotBlank
    private Boolean booked;

    public boolean isValid(){
        return flightNumber != null && departureTime != null && arrivalTime != null && flightType != null && status != null && price != 0;
    }
}
