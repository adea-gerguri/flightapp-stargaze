package ticket.service;

import com.mongodb.reactivestreams.client.ClientSession;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.client.Client;
import org.bson.Document;
import org.bson.conversions.Bson;
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
                .flatMap(validatedDto ->{
                    return repository.addTicket(TicketMapper.toTicket(validatedDto));
                });
    }

    public Uni<DeleteResult> deleteTicketById(String id){

        return repository.deleteTicket(id)
                .onItem()
                .transform(deleteResult -> {
                    return deleteResult;
                });
    }

    public Uni<List<TicketDto>> getCheapestTicket() {
        return repository.getCheapestTicket()
                .onItem().transform(ticketDto -> {
                    return ticketDto;
                });
    }

    public Uni<List<TicketDto>> getMostExpensiveTicket() {
        return repository.getMostExpensiveTicket()
                .onItem().transform(ticketDto -> {
                    return ticketDto;
                });
    }

    public Uni<TicketEntity> findAvailableTicketByFlightNumber(String flightNumber, ClientSession clientSession) {
        return repository.findAvailableTicketByFlightNumber(flightNumber, clientSession);
    }

    public Uni<TicketEntity> findTicketByFlightNumber(String flightNumber, String userId, ClientSession clientSession){
        return repository.findTicketByFlightNumber(flightNumber, userId, clientSession);
    }

    public Uni<UpdateResult> updateTicket(String ticketId, String userId, String reservationId, double totalPrice, ClientSession clientSession ) {
        return repository.updateTicket(ticketId, userId, reservationId, totalPrice, clientSession);
    }
}
