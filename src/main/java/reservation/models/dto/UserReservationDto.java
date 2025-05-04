package reservation.models.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserReservationDto {
    @NotNull private String userId;
    @NotNull private long reservationCount;
    @NotNull private long refundCount;
}
