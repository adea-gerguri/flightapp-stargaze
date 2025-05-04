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
    private String userId;
    private String reservationId;
    private String flightNumber;
    private double price;
}
