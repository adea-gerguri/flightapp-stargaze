package ticket.repository;

import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.conversions.Bson;
import shared.mongoUtils.DeleteResult;
import shared.mongoUtils.InsertResult;
import shared.mongoUtils.MongoUtil;
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

    public Uni<TicketDto> getCheapestTicket() {
        List<Bson> pipeline = Arrays.asList(
                Aggregates.sort(Sorts.ascending("price")),
                Aggregates.limit(1),
                Aggregates.project(Projections.fields(
                        Projections.include("firstName", "lastName", "passportNumber", "price", "baggageEntityList"),
                        Projections.excludeId()
                ))
        );

        return getCollection()
                .aggregate(pipeline)
                .collect().first()
                .onItem().transform(TicketMapper::toTicketDto);
    }

    public Uni<TicketDto> getMostExpensiveTicket() {
        List<Bson> pipeline = Arrays.asList(
                Aggregates.sort(Sorts.descending("price")),
                Aggregates.limit(1),
                Aggregates.project(Projections.fields(
                        Projections.include("firstName", "lastName", "passportNumber", "price", "baggageEntityList"),
                        Projections.excludeId()
                ))
        );

        return getCollection()
                .aggregate(pipeline)
                .collect().first()
                .onItem().transform(TicketMapper::toTicketDto);
    }



    private ReactiveMongoCollection<TicketEntity> getCollection() {
        return mongoUtil.getCollection("tickets", TicketEntity.class);
    }

}
