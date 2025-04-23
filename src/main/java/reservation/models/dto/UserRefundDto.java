package reservation.models.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import reservation.enums.ReservationStatus;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserRefundDto {
    @NotNull private String userId;
    @NotNull private long count;
    @NotNull private long refundCount;
    @NotNull  private ReservationStatus reservationStatus;
}
