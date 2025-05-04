package flight.models.dto;

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
public class StopoverDto {
    @NotNull private String departureAirportId;
    @NotNull private String arrivalAirportId;
    @NotNull private LocalDateTime departureTime;
    @NotNull private LocalDateTime arrivalTime;
    @NotNull private double price;
}


