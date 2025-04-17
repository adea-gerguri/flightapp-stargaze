package ticket.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import baggage.models.BaggageEntity;
import ticket.models.TicketEntity;
import org.bson.types.ObjectId;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {
    private String reservationId;
    private String firstName;
    private String lastName;
    private String passportNumber;
    private List<BaggageEntity> baggageEntityList;
    private double price;

}
