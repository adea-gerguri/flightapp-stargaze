package flight.models.dto;

import flight.enums.FlightType;
import flight.models.FlightEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StopoverDto {
    private String departureAirportId;
    private String arrivalAirportId;
    private String departureTime;
    private String arrivalTime;
    private double price;
}


