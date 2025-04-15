package reservation.models.dto;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import baggage.models.BaggageEntity;
import reservation.enums.SeatType;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {
    @NotBlank
    private Date reservationDate;

    @NotBlank
    private Map<Integer, SeatType> seatSelection;

    @NotBlank
    private boolean specialAssistance;

    @NotBlank
    private BaggageEntity baggageEntity;
}
