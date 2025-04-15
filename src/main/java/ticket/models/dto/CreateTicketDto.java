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

    public boolean isValid(){
        return reservationId!=null && firstName!=null && lastName!=null && passportNumber!=null;
    }
}
