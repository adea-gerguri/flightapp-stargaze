package reservation.repository;

import com.mongodb.client.model.*;
import com.mongodb.reactivestreams.client.ClientSession;
import io.quarkus.mongodb.FindOptions;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.conversions.Bson;
import reservation.models.ReservationEntity;
import reservation.models.dto.ReservationDto;
import reservation.models.dto.UserReservationDto;
import shared.PaginationQueryParams;
import shared.mongoUtils.InsertResult;
import shared.mongoUtils.MongoUtil;

import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class ReservationRepository {
    @Inject
    MongoUtil mongoService;


    public Uni<List<ReservationEntity>> listReservations(PaginationQueryParams paginationQueryParams) {
        return MongoUtil.listAll(getCollection(), new FindOptions().skip(paginationQueryParams.getSkip()).limit(paginationQueryParams.getLimit()));
    }

    public Uni<InsertResult> insertReservation(ReservationEntity reservation, ClientSession clientSession){
        return MongoUtil.insertOne(getCollection(), reservation, clientSession);
    }

    private ReactiveMongoCollection<ReservationEntity> getCollection() {
        return mongoService.getCollection("reservations", ReservationEntity.class);
    }

    private ReactiveMongoCollection<ReservationDto> getCollectionDto(){
        return mongoService.getCollection("reservations",ReservationDto.class);
    }

    public Uni<ReservationDto> findByUserIdAndFlightNumber(String userId, String flightNumber) {
        Bson filter = Filters.and(
                Filters.eq("userId", userId),
                Filters.eq("flightNumber", flightNumber)
        );

        return MongoUtil.findOneByFilter(getCollectionDto(), filter);

    }

    public Uni<List<UserReservationDto>> findUserWithMostReservations() {
        List<Bson> pipeline = Arrays.asList(
                Aggregates.group("$userId", Accumulators.sum("reservationCount", 1)),
                Aggregates.sort(Sorts.descending("reservationCount")),
                Aggregates.limit(1),
                Aggregates.project(Projections.fields(
                        Projections.excludeId(),
                        Projections.include("reservationCount"),
                        Projections.computed("userId", "$userId")
                ))
        );

        return MongoUtil.aggregate(getCollectionDto(), pipeline, UserReservationDto.class);

    }

    public Uni<List<UserReservationDto>> findUserWithMostRefundedTickets() {
        List<Bson> pipeline = Arrays.asList(
                Aggregates.match(Filters.eq("reservationStatus", "RESERVED")),
                Aggregates.group("$userId", Accumulators.sum("refundCount", 1)),
                Aggregates.sort(Sorts.descending("refundCount")),
                Aggregates.limit(1),
                Aggregates.project(Projections.fields(
                        Projections.excludeId(),
                        Projections.include("refundCount"),
                        Projections.computed("userId", "$userId")
                ))
        );

        return MongoUtil.aggregate(getCollectionDto(), pipeline, UserReservationDto.class);
    }

}

