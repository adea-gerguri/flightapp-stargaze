package reservation.repository;

import com.mongodb.client.model.*;
import io.quarkus.mongodb.FindOptions;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.conversions.Bson;
import reservation.enums.ReservationStatus;
import reservation.exception.ReservationException;
import reservation.mappers.ReservationMapper;
import reservation.models.ReservationEntity;
import reservation.models.dto.ReservationDto;
import reservation.models.dto.UserReservationDto;
import shared.mongoUtils.InsertResult;
import shared.mongoUtils.MongoUtil;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class ReservationRepository {
    @Inject
    MongoUtil mongoService;


    public Uni<List<ReservationEntity>> listReservations(int skip, int limit) {
        return MongoUtil.listAll(getCollection(), new FindOptions().skip(skip).limit(limit));
    }

    public Uni<InsertResult> insertReservation(ReservationEntity reservation){
        return MongoUtil.insertOne(getCollection(), reservation);
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
        return getCollectionDto()
                .find(filter)
                .collect()
                .first()
                .onItem().invoke(reservation -> {
                    if (reservation == null) {
                        System.out.println("No reservation found for filter: " + filter);
                    } else {
                        System.out.println("Found reservation: " + reservation);
                    }
                });

    }

    public Uni<UserReservationDto> findUserWithMostReservations() {
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

        return getCollectionDto()
                .aggregate(pipeline, UserReservationDto.class)
                .collect()
                .first()
                .onItem().transform(reservation -> {
                    if (reservation != null) {
                        return reservation;
                    } else {
                        throw new ReservationException("No reservations found", 404);
                    }
                })
                .onFailure(ReservationException.class)
                .recoverWithItem(() -> {
                    throw new ReservationException("No reservations found", 404);
                });
    }

    public Uni<UserReservationDto> findUserWithMostRefundedTickets() {
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

        return getCollectionDto()
                .aggregate(pipeline, UserReservationDto.class)
                .collect()
                .first()
                .onItem().transform(reservation -> {
                    if (reservation != null) {
                        return reservation;
                    } else {
                        throw new ReservationException("No refunded reservations found", 404);
                    }
                })
                .onFailure(ReservationException.class)
                .recoverWithItem(() -> {
                    throw new ReservationException("No refunded reservations found", 404);
                });
    }




}

