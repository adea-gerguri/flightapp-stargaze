package repository;

import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import model.dto.TicketDto;
import model.mapper.MapperService;
import model.mapper.TicketMapper;
import model.view.Baggage;
import model.view.Ticket;

import org.bson.Document;
import java.util.List;

@ApplicationScoped
public class TicketRepository {

    @Inject
    ReactiveMongoClient mongoClient;

    @Inject
    MapperService mapper;

    public Uni<List<TicketDto>> listTickets(){
        return getCollection().find()
                .map(document->{
                    Ticket ticket = new Ticket();
                    ticket.setFirstName(document.getString("firstName"));
                    ticket.setLastName(document.getString("lastName"));
                    ticket.setPassportNumber(document.getString("passportNumber"));
                    ticket.setBaggageList((List<Baggage>) document.get("baggageList"));
                    ticket.setPrice(document.getDouble("price"));
                    return mapper.map(ticket, TicketDto.class);
                }).collect().asList();
    }
    public Uni<Void> addTicket(Ticket ticket){
        Document document = new Document()
                .append("firstName", ticket.getFirstName())
                .append("lastName", ticket.getLastName())
                .append("passportNumber", ticket.getPassportNumber())
                .append("baggageList", ticket.getBaggageList())
                .append("price", ticket.getPrice());
        return getCollection().insertOne(document)
                .onItem().ignore().andContinueWithNull();
    }
    private ReactiveMongoCollection<Document> getCollection(){
        return mongoClient.getDatabase("stargaze").getCollection("tickets");
    }
}
