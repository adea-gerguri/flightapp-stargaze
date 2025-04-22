package reservation.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import reservation.enums.ReservationStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRefundDto {
    private String userId;
    private long count;
    private ReservationStatus reservationStatus;
}
