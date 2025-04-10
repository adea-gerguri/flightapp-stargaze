package model.mapper;

import jakarta.enterprise.context.RequestScoped;
import model.dto.TicketDto;
import model.view.Ticket;

@RequestScoped
public class TicketMapper {
    public Ticket toTicket(TicketDto ticketDto){
        if(ticketDto!=null){
            Ticket ticket = new Ticket();
            ticket.setId(ticketDto.getId());
            ticket.setFirstName(ticketDto.getFirstName());
            ticket.setLastName(ticketDto.getLastName());
            ticket.setBaggageList(ticketDto.getBaggageList());
            ticket.setPrice(ticketDto.getPrice());
            ticket.setPassportNumber(ticketDto.getPassportNumber());
            return ticket;
        }
        return null;
    }
    public TicketDto toTicketDto(Ticket ticket){
        if(ticket!=null){
            TicketDto ticketDto = new TicketDto();
            ticketDto.setId(ticket.getId());
            ticketDto.setFirstName(ticket.getFirstName());
            ticketDto.setLastName(ticket.getLastName());
            ticketDto.setBaggageList(ticket.getBaggageList());
            ticketDto.setPrice(ticket.getPrice());
            ticketDto.setPassportNumber(ticket.getPassportNumber());
            return ticketDto;
        }
        return null;
    }
}
