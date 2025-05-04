package flight.models.dto;

import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DateQueryParams {
    @QueryParam("fromDate")private LocalDateTime fromDate;

    @QueryParam("toDate") private LocalDateTime toDate;
}
