package ticket.models.dto;

import baggage.models.BaggageEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateTicketDto {
    private String reservationId;
    private String firstName;
    private String lastName;
    private String passportNumber;
    private List<BaggageEntity> baggageEntityList;
    private double price;
}
