package reservation.models.dto;

import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RevenueQueryParams {
    @QueryParam("fromDateTime")private LocalDateTime fromDateTime;

    @QueryParam("toDateTime")private LocalDateTime toDateTime;

    @QueryParam("timeFormat") private String timeFormat;
}
