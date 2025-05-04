package flight.models.dto;

import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RouteQueryParams {
    @QueryParam("departureAirportId")private String departureAirportId;

    @QueryParam("destinationAirportId")private String destinationAirportId;

    @QueryParam("departureDate")private LocalDateTime departureDate;

    @QueryParam("arrivalDate") private LocalDateTime arrivalDate;
}
