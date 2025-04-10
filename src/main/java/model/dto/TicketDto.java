package model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.view.Baggage;
import model.view.Ticket;
import org.bson.types.ObjectId;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {
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
    private List<Baggage> baggageList;

    @NotBlank(message="Ticket should have a price")
    private double price;

    public TicketDto(Ticket ticket){
        this.setId(ticket.getId());
        this.setFirstName(ticket.getFirstName());
        this.setLastName(ticket.getLastName());
        this.setPassportNumber(ticket.getPassportNumber());
        this.setBaggageList(ticket.getBaggageList());
        this.setPrice(ticket.getPrice());
        this.setReservationId(ticket.getReservationId());
    }
}
