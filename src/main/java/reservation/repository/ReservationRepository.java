package reservation.repository;

import airline.models.AirlineEntity;
import io.quarkus.mongodb.FindOptions;
import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import reservation.exception.ReservationFailedException;
import reservation.models.dto.ReservationDto;
import reservation.mappers.ReservationMapper;
import baggage.models.BaggageEntity;
import reservation.models.ReservationEntity;
import org.bson.Document;
import reviews.models.ReviewEntity;
import shared.mongoUtils.InsertResult;
import shared.mongoUtils.MongoUtil;

import java.sql.Date;
import java.util.List;

import static shared.mongoUtils.MongoUtil.findOne;

@ApplicationScoped
public class ReservationRepository {
    @Inject
    MongoUtil mongoService;


    public Uni<List<ReservationEntity>> listReservations() {
        return MongoUtil.listAll(getCollection(), new FindOptions());
    }

    private ReactiveMongoCollection<ReservationEntity> getCollection() {
        return mongoService.getCollection("reservations", ReservationEntity.class);
    }


}

