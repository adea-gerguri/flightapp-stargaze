package reservation.repository;

import io.quarkus.mongodb.FindOptions;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import reservation.models.ReservationEntity;
import shared.mongoUtils.MongoUtil;

import java.util.List;

@ApplicationScoped
public class ReservationRepository {
    @Inject
    MongoUtil mongoService;


    public Uni<List<ReservationEntity>> listReservations(int skip, int limit) {
        return MongoUtil.listAll(getCollection(), new FindOptions().skip(skip).limit(limit));
    }

    private ReactiveMongoCollection<ReservationEntity> getCollection() {
        return mongoService.getCollection("reservations", ReservationEntity.class);
    }


}

