package ticket.models.dto;

import baggage.models.BaggageEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CreateTicketDto {
    private String firstName;
    private String lastName;
    private String passportNumber;
    private List<BaggageEntity> baggageEntityList;
    private double price;
}
