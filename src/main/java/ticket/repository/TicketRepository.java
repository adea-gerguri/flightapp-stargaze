package ticket.repository;

import com.mongodb.client.model.*;
import com.mongodb.reactivestreams.client.ClientSession;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.conversions.Bson;
import shared.mongoUtils.DeleteResult;
import shared.mongoUtils.InsertResult;
import shared.mongoUtils.MongoUtil;
import shared.mongoUtils.UpdateResult;
import ticket.mappers.TicketMapper;
import ticket.models.TicketEntity;
import ticket.models.dto.TicketDto;

import java.util.Arrays;
import java.util.List;


@ApplicationScoped
public class TicketRepository {
    @Inject
    MongoUtil mongoUtil;

    public Uni<InsertResult> addTicket(TicketEntity ticketEntity) {
        return MongoUtil.insertOne(getCollection(), ticketEntity);
    }

    public Uni<DeleteResult> deleteTicket(String id){
        return MongoUtil.deleteOne(getCollection(),id );
    }

    public Uni<List<TicketDto>> getCheapestTicket() {
        List<Bson> pipeline = Arrays.asList(
                Aggregates.sort(Sorts.ascending("price")),
                Aggregates.limit(1),
                Aggregates.project(Projections.fields(
                        Projections.include("firstName", "lastName", "passportNumber", "price", "baggageEntityList"),
                        Projections.excludeId()
                ))
        );
        return MongoUtil.aggregate(getCollection(), pipeline, TicketDto.class);
    }

    public Uni<List<TicketDto>> getMostExpensiveTicket() {
        List<Bson> pipeline = Arrays.asList(
                Aggregates.sort(Sorts.descending("price")),
                Aggregates.limit(1),
                Aggregates.project(Projections.fields(
                        Projections.include("firstName", "lastName", "passportNumber", "price", "baggageEntityList"),
                        Projections.excludeId()
                ))
        );

        return MongoUtil.aggregate(getCollection(), pipeline, TicketDto.class);
    }

    public Uni<TicketEntity> findAvailableTicketByFlightNumber(String flightNumber, ClientSession clientSession){
        Bson filter = Filters.and(
            Filters.eq("flightNumber", flightNumber),
            Filters.eq("userId", null)
        );
        return MongoUtil.findOneByFilter(getCollection(), filter, clientSession);
    }

    public Uni<TicketEntity> findTicketByFlightNumber(String flightNumber, String userId){
        Bson filter = Filters.and(
                Filters.eq("flightNumber", flightNumber),
                Filters.eq("userId", userId)
        );
        return MongoUtil.findOneByFilter(getCollection(), filter);
    }

    public Uni<UpdateResult> updateTicket(String ticketId, String userId, String reservationId, double totalPrice, ClientSession clientSession) {
        Bson filter = Filters.eq("_id", ticketId);
        Bson update = Updates.combine(
                Updates.set("userId", userId),
                Updates.set("reservationId", reservationId),
                Updates.set("price", totalPrice)
        );
        return MongoUtil.updateOne(getCollectionDto(), filter, update, clientSession);
    }

    private ReactiveMongoCollection<TicketEntity> getCollection() {
        return mongoUtil.getCollection("tickets", TicketEntity.class);
    }
    private ReactiveMongoCollection<TicketDto> getCollectionDto() {
        return mongoUtil.getCollection("tickets", TicketDto.class);
    }

}
