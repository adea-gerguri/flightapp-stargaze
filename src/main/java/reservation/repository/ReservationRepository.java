package reservation.repository;

import com.mongodb.client.model.*;
import com.mongodb.reactivestreams.client.ClientSession;
import reservation.models.dto.RevenueAggregationDto;
import io.quarkus.mongodb.FindOptions;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.conversions.Bson;
import reservation.models.ReservationEntity;
import reservation.models.dto.RevenueQueryParams;
import reservation.models.dto.ReservationDto;
import reservation.models.dto.ReservationRevenueDto;
import reservation.models.dto.UserReservationDto;
import shared.PaginationQueryParams;
import shared.mongoUtils.InsertResult;
import shared.mongoUtils.MongoUtil;
import org.bson.Document;

import java.time.LocalDateTime;
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

    public Uni<List<UserReservationDto>> findUserWithMostRefundedTickets() {
        List<Bson> pipeline = Arrays.asList(
                Aggregates.match(Filters.eq("reservationStatus", "REFUNDED")),
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
    public Uni<List<UserReservationDto>> findUserWithMostReservedTickets() {
        List<Bson> pipeline = Arrays.asList(
                Aggregates.match(Filters.eq("reservationStatus", "RESERVED")),
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

    public Uni<List<ReservationRevenueDto>> salesOverTime(RevenueQueryParams revenueQueryParams){
        LocalDateTime from = revenueQueryParams.getFromDateTime();
        LocalDateTime to = revenueQueryParams.getToDateTime();
        List<Bson> pipeline = Arrays.asList(
                Aggregates.match(
                        Filters.and(
                                Filters.gte("reservationDate", from),
                                Filters.lte("reservationDate", to)
                        )),
                Aggregates.group(
                        new Document("$dateToString", new Document("format", "%Y-%m-%d").append("date", "$reservationDate")),
                        Accumulators.sum("totalPrice","$price")
                ),
                Aggregates.project(
                        Projections.fields(
                                Projections.computed("timePeriod","$_id"),
                                Projections.include("totalSales")
                        )
                )
        );
        return MongoUtil.aggregate(getCollectionDto(), pipeline, ReservationRevenueDto.class);
    }

    public Uni<List<RevenueAggregationDto>> revenueOverTime(RevenueQueryParams revenueQueryParams){
        LocalDateTime fromDateTime = revenueQueryParams.getFromDateTime();
        LocalDateTime toDateTime = revenueQueryParams.getToDateTime();
        String timeFormat = revenueQueryParams.getTimeFormat();

        List<Bson> pipeline = Arrays.asList(
                Aggregates.match(
                        Filters.and(
                                Filters.gte("reservationDate", fromDateTime),
                                Filters.lte("reservationDate", toDateTime)
                        )
                ),
                Aggregates.group(
                        new Document("$dateToString", new Document("timeFormat", timeFormat).append("date", "$reservationDate")),
                        Accumulators.sum("totalRevenue","$price")
                ),
                Aggregates.project(
                        Projections.fields(
                                Projections.computed("timePeriod", "$_id"),
                                Projections.include("totalRevenue")
                        )
                ),
                Aggregates.sort(Sorts.ascending("timePeriod"))
        );

        return MongoUtil.aggregate(getCollectionDto(), pipeline, RevenueAggregationDto.class);
    }

    public Uni<List<ReservationRevenueDto>> facetedRevenue(RevenueQueryParams revenueQueryParams){
        LocalDateTime from = revenueQueryParams.getFromDateTime();
        LocalDateTime to = revenueQueryParams.getToDateTime();

        List<Bson> pipeline = Arrays.asList(
                Aggregates.match(
                        Filters.and(
                                Filters.gte("reservationDate", from),
                                Filters.lte("reservationDate",to)
                        )
                ),
                Aggregates.facet(
                        new Facet("salesOverTime", Arrays.asList(
                                Aggregates.group(
                                        new Document("$dateToString", new Document("format", "%Y-%m-%d")
                                                .append("date", "$reservationDate")),
                                        Accumulators.sum("totalSales", "$price")
                                ),
                                Aggregates.project(Projections.fields(
                                        Projections.computed("timePeriod", "$_id"),
                                        Projections.include("totalSales")
                                ))
                        )),
                        new Facet("totalReservations",
                                Aggregates.count("count")
                        ),
                        new Facet("averagePrice",
                                Aggregates.group(null, Accumulators.avg("avgPrice", "$price"))
                        )
                )
        );

        return MongoUtil.aggregate(getCollectionDto(), pipeline, ReservationRevenueDto.class);
    }
}

