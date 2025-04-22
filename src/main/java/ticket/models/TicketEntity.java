package ticket.models;

import baggage.models.BaggageEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import ticket.models.dto.TicketDto;
import org.bson.types.ObjectId;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketEntity {
    private String id;
    private String firstName;
    private String lastName;
    private String passportNumber;
    private List<BaggageEntity> baggageEntityList;
    private double price;
}
