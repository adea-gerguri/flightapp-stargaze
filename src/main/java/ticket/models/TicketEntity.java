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
    @NotBlank
    private String id;

    @NotBlank
    private String reservationId;

    @NotBlank(message="First Name should be assigned to a ticket")
    private String firstName;

    @NotBlank(message="Last Name should be assigned to a ticket")
    private String lastName;

    @NotBlank(message="Ticket should be assigned to a specific passport")
    private String passportNumber;

    private List<BaggageEntity> baggageEntityList;

    @NotBlank(message="Ticket should have a price")
    private double price;

}
