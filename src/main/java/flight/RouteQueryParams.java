package flight;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RouteQueryParams {
    @QueryParam("departureAirportId")private String departureAirportId;

    @QueryParam("destinationAirportId")private String destinationAirportId;

    @QueryParam("departureDate")private String departureDate;

    @QueryParam("arrivalDate") private String arrivalDate;
}
