package ticket.repository;

import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import shared.mongoUtils.DeleteResult;
import shared.mongoUtils.InsertResult;
import shared.mongoUtils.MongoUtil;
import ticket.models.TicketEntity;


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


    private ReactiveMongoCollection<TicketEntity> getCollection() {
        return mongoUtil.getCollection("tickets", TicketEntity.class);
    }

}
