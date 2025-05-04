package ticket.mappers;

import ticket.models.dto.CreateTicketDto;
import ticket.models.TicketEntity;
import ticket.models.dto.TicketDto;

public class TicketMapper {
    public static TicketEntity toTicket(CreateTicketDto ticketDto){
        if(ticketDto!=null){
            TicketEntity ticketEntity = new TicketEntity();
            ticketEntity.setReservationId(ticketDto.getReservationId());
            ticketEntity.setFlightNumber(ticketDto.getFlightNumber());
            ticketEntity.setPrice(ticketDto.getPrice());
            return ticketEntity;
        }
        return null;
    }
    public static CreateTicketDto toCreateTicketDto(TicketEntity ticketEntity){
        if(ticketEntity !=null){
            CreateTicketDto ticketDto = new CreateTicketDto();
            ticketDto.setPrice(ticketEntity.getPrice());
            ticketDto.setReservationId(ticketEntity.getReservationId());
            ticketDto.setFlightNumber(ticketEntity.getFlightNumber());
            ticketDto.setUserId(ticketEntity.getUserId());
            return ticketDto;
        }
        return null;
    }
    public static TicketDto toTicketDto(TicketEntity ticketEntity){
        if(ticketEntity !=null){
            TicketDto ticketDto = new TicketDto();
            ticketDto.setPrice(ticketEntity.getPrice());
            ticketDto.setReservationId(ticketEntity.getReservationId());
            ticketDto.setFlightNumber(ticketEntity.getFlightNumber());
            ticketDto.setUserId(ticketEntity.getUserId());
            return ticketDto;
        }
        return null;
    }
}
