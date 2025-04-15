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
