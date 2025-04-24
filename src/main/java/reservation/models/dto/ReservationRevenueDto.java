package reservation.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationRevenueDto {
    private String timePeriod;
    private double totalSales;
}
