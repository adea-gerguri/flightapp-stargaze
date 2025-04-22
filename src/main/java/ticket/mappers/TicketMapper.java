package ticket.mappers;

import ticket.models.dto.CreateTicketDto;
import ticket.models.TicketEntity;
import ticket.models.dto.TicketDto;

public class TicketMapper {
    public static TicketEntity toTicket(CreateTicketDto ticketDto){
        if(ticketDto!=null){
            TicketEntity ticketEntity = new TicketEntity();
            ticketEntity.setFirstName(ticketDto.getFirstName());
            ticketEntity.setLastName(ticketDto.getLastName());
            ticketEntity.setBaggageEntityList(ticketDto.getBaggageEntityList());
            ticketEntity.setPrice(ticketDto.getPrice());
            ticketEntity.setPassportNumber(ticketDto.getPassportNumber());
            return ticketEntity;
        }
        return null;
    }
    public static CreateTicketDto toCreateTicketDto(TicketEntity ticketEntity){
        if(ticketEntity !=null){
            CreateTicketDto ticketDto = new CreateTicketDto();
            ticketDto.setFirstName(ticketEntity.getFirstName());
            ticketDto.setLastName(ticketEntity.getLastName());
            ticketDto.setBaggageEntityList(ticketEntity.getBaggageEntityList());
            ticketDto.setPrice(ticketEntity.getPrice());
            ticketDto.setPassportNumber(ticketEntity.getPassportNumber());
            return ticketDto;
        }
        return null;
    }
    public static TicketDto toTicketDto(TicketEntity ticketEntity){
        if(ticketEntity !=null){
            TicketDto ticketDto = new TicketDto();
            ticketDto.setFirstName(ticketEntity.getFirstName());
            ticketDto.setLastName(ticketEntity.getLastName());
            ticketDto.setBaggageEntityList(ticketEntity.getBaggageEntityList());
            ticketDto.setPrice(ticketEntity.getPrice());
            ticketDto.setPassportNumber(ticketEntity.getPassportNumber());
            return ticketDto;
        }
        return null;
    }
}
