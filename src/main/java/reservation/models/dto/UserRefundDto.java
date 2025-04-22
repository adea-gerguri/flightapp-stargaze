package reservation.models.dto;

import lombok.*;
import reservation.enums.ReservationStatus;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserRefundDto {
    private String userId;
    private long count;
    private long refundCount;
    private ReservationStatus reservationStatus;
}
