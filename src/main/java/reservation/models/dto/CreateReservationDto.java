package reservation.models.dto;

import baggage.models.BaggageEntity;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import reservation.enums.SeatType;

import java.util.Date;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateReservationDto {
    private Date reservationDate;

    private String userId;

    @NotBlank
    private Map<Integer, SeatType> seatSelection;

    @NotBlank
    private boolean specialAssistance;

    private BaggageEntity baggage;
    public boolean isValid(){
        return   seatSelection != null ;
    }
}
