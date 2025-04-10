package model.view;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.dto.TicketDto;
import org.bson.types.ObjectId;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @NotBlank
    private ObjectId id;

    @NotBlank
    private ObjectId reservationId;

    @NotBlank(message="First Name should be assigned to a ticket")
    private String firstName;

    @NotBlank(message="Last Name should be assigned to a ticket")
    private String lastName;

    @NotBlank(message="Ticket should be assigned to a specific passport")
    private String passportNumber;

    @NotBlank
    private List<Baggage> baggageList;

    @NotBlank(message="Ticket should have a price")
    private double price;

    public Ticket(TicketDto ticketDto){
        this.setId(ticketDto.getId());
        this.setReservationId(ticketDto.getReservationId());
        this.setFirstName(ticketDto.getFirstName());
        this.setLastName(ticketDto.getLastName());
        this.setPassportNumber(ticketDto.getPassportNumber());
        this.setBaggageList(ticketDto.getBaggageList());
        this.setPrice(ticketDto.getPrice());
    }
}
