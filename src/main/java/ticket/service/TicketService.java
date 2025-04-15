package ticket.service;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import org.bson.Document;
import shared.mongoUtils.DeleteResult;
import shared.mongoUtils.InsertResult;
import shared.mongoUtils.UpdateResult;
import ticket.mappers.TicketMapper;
import ticket.models.TicketEntity;
import ticket.models.dto.CreateTicketDto;
import ticket.models.dto.TicketDto;
import ticket.repository.TicketRepository;

import java.util.List;

@ApplicationScoped
public class TicketService {

    @Inject
    TicketRepository repository;

    public Uni<InsertResult> addTicket(CreateTicketDto ticketDto) {
        if (!ticketDto.isValid()) {
            return Uni.createFrom().failure(new BadRequestException("Ticket not valid!"));
        }
        return Uni.createFrom()
                .item(TicketMapper.toTicket(ticketDto))
                .flatMap(ticket -> repository.addTicket(ticket));
    }

    public Uni<DeleteResult> deleteTicketById(String id){
        return repository.deleteTicket(id);
    }


}
