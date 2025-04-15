package flight.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import flight.enums.FlightType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateFlightDto {
    @NotBlank
    private String flightNumber;

    private String departureAirportId;

    private String arrivalAirportId;

    @NotBlank
    private String departureTime;

    @NotBlank
    private String arrivalTime;


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
        return flightNumber != null  && departureTime != null && arrivalTime != null && price != 0;
    }
}
