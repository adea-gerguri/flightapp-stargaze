package ticket.models;

import baggage.models.BaggageEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ticket.models.dto.TicketDto;
import org.bson.types.ObjectId;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketEntity {
    private String id;
    private String reservationId;
    private String firstName;
    private String lastName;
    private String passportNumber;
    private List<BaggageEntity> baggageEntityList;
    private double price;

}
