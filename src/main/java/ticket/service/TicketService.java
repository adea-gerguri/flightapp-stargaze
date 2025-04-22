package ticket.service;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.BadRequestException;
import org.bson.Document;
import shared.GlobalHibernateValidator;
import shared.mongoUtils.DeleteResult;
import shared.mongoUtils.InsertResult;
import shared.mongoUtils.UpdateResult;
import ticket.exceptions.TicketException;
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


    @Inject
    GlobalHibernateValidator validator;

    public Uni<InsertResult> addTicket(CreateTicketDto ticketDto) {
        return validator.validate(ticketDto)
                .onFailure(ConstraintViolationException.class)
                .transform(e->new TicketException(e.getMessage(), 400))
                .flatMap(validatedDto ->{
                    return repository.addTicket(TicketMapper.toTicket(validatedDto));
                });
    }

    public Uni<DeleteResult> deleteTicketById(String id){

        return repository.deleteTicket(id)
                .onItem()
                .transform(deleteResult->{
                    if(deleteResult.getDeletedCount() == 0){
                        throw new TicketException("Ticket not found", 404);
                    }
                    return deleteResult;
                });
    }

    public Uni<TicketDto> getCheapestTicket() {
        return repository.getCheapestTicket()
                .onItem().transform(ticketDto -> {
                    System.out.println("Cheapest Ticket: " + ticketDto);
                    return ticketDto;
                })
                .onFailure().recoverWithItem(ex -> {
                    return null;
                });
    }

    public Uni<TicketDto> getMostExpensiveTicket() {
        return repository.getMostExpensiveTicket()
                .onItem().transform(ticketDto -> {
                    System.out.println("Most Expensive Ticket: " + ticketDto);
                    return ticketDto;
                })
                .onFailure().recoverWithItem(ex -> {
                    return null;
                });
    }
}
