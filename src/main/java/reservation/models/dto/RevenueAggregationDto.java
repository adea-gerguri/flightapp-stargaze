package reservation.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RevenueAggregationDto {
    private String timePeriod;
    private double totalRevenue;
}
