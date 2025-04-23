package flight.repository;

import com.mongodb.client.model.*;
import com.mongodb.reactivestreams.client.ClientSession;
import flight.RouteQueryParams;
import flight.exceptions.FlightException;
import flight.mappers.FlightMapper;
import flight.models.dto.BookStatusFlightDto;
import flight.models.dto.CreateFlightDto;
import flight.models.dto.FlightDto;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import flight.models.FlightEntity;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import shared.mongoUtils.DeleteResult;
import shared.mongoUtils.InsertResult;
import shared.mongoUtils.MongoUtil;
import shared.mongoUtils.UpdateResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@ApplicationScoped
public class FlightRepository {

    @Inject
    MongoUtil mongoService;

    public Uni<List<FlightDto>> listLowestPrice(int skip, int limit) {
        List<Bson> pipeline = List.of(
                Aggregates.sort(Sorts.ascending("price")),
                Aggregates.skip(skip),
                Aggregates.limit(limit),
                Aggregates.project(Projections.fields(
                        Projections.computed("id", "$_id"),
                        Projections.include("name", "price")
                ))
        );
        return MongoUtil.aggregate(getCollection(), pipeline, FlightDto.class);
    }

    public Uni<List<FlightDto>> findFastestRoute(RouteQueryParams routeQueryParams) {
        List<Bson> pipeline = List.of(
                Aggregates.match(Filters.and(
                        Filters.eq("arrivalAirportId", routeQueryParams.getDestinationAirportId()),
                        Filters.eq("departureAirportId", routeQueryParams.getDepartureAirportId()),
                        Filters.regex("departureTime", routeQueryParams.getDepartureDate())
                )),
                Aggregates.project(Projections.fields(
                        Projections.include("flightNumber", "departureAirportId", "arrivalAirportId", "departureTime", "arrivalTime"),
                        Projections.computed("durations", "{$subtract: [ { $toDate: '$arrivalTime' }, { $toDate: '$departureTime' } ]}")
                )),
                Aggregates.sort(Sorts.ascending("duration")),
                Aggregates.limit(1)
        );

        return MongoUtil.aggregate(getCollection(), pipeline, FlightDto.class);
    }

    public Uni<Boolean> existsByFlightNumber(String flightNumber) {
        Bson filter = Filters.eq("flightNumber", flightNumber);
        return getCollectionFlight().countDocuments(filter)
                .onItem().transform(count -> count > 0);
    }


    public Uni<List<FlightDto>> findCheapestRoute(String destinationAirportId, String departureDate, String departureAirportId) {
        List<Bson> pipeline = List.of(
                Aggregates.match(Filters.and(
                        Filters.eq("arrivalAirportId", destinationAirportId),
                        Filters.eq("departureAirportId", departureAirportId),
                        Filters.regex("departureTime", departureDate)
                )),
                Aggregates.project(Projections.fields(
                        Projections.include("flightNumber", "departureAirportId", "arrivalAirportId", "departureTime", "arrivalTime", "price"),
                        Projections.computed("duration", "{ $subtract: [ { $toDate: '$arrivalTime' }, { $toDate: '$departureTime' } ] }")
                )),
                Aggregates.sort(Sorts.ascending("price")),
                Aggregates.limit(1)
        );

        return MongoUtil.aggregate(getCollection(), pipeline, FlightDto.class);
    }

    public Uni<List<FlightDto>> findMostExpensiveRoute(String destinationAirportId, String departureDate, String departureAirportId) {
        List<Bson> pipeline = List.of(
                Aggregates.match(Filters.and(
                        Filters.eq("arrivalAirportId", destinationAirportId),
                        Filters.eq("departureAirportId", departureAirportId),
                        Filters.regex("departureTime", departureDate)
                )),
                Aggregates.project(Projections.fields(
                        Projections.include("flightNumber", "departureAirportId", "arrivalAirportId", "departureTime", "arrivalTime", "price"),
                        Projections.computed("duration", "{ $subtract: [ { $toDate: '$arrivalTime' }, { $toDate: '$departureTime' } ] }")
                )),
                Aggregates.sort(Sorts.descending("price")),
                Aggregates.limit(1)
        );

        return MongoUtil.aggregate(getCollection(), pipeline, FlightDto.class);
    }

    public Uni<List<FlightDto>> findFlightsWithStopsAndWaitingTimes(String departureAirportId, String destinationAirportId, String departureDate) {
        List<Bson> pipeline = new ArrayList<>();

        pipeline.add(
                Aggregates.match(Filters.and(
                        Filters.eq("departureAirportId", departureAirportId),
                        Filters.eq("arrivalAirportId", destinationAirportId),
                        Filters.regex("departureTime", departureDate)
                )));
        pipeline.add(
                Aggregates.graphLookup("flights",
                        "$arrivalAirportId",
                        "departureAirportId",
                        "arrivalAirportId",
                        "connections",
                        new GraphLookupOptions()
                                .maxDepth(2)
                                .restrictSearchWithMatch(Filters.regex("departureTime", departureDate))
                ));
        pipeline.add(Aggregates.project(Projections.fields(
                Projections.computed("fullPath", new Document("$concatArrays", List.of(
                        List.of(new Document("departureAirportId", "$departureAirportId")
                                .append("arrivalAirportId", "$arrivalAirportId")
                                .append("departureTime", "$departureTime")
                                .append("arrivalTime", "$arrivalTime")
                                .append("price", "$price")),
                        "$connections"
                )))
        )));
        pipeline.add(Aggregates.project(Projections.fields(
                Projections.include("fullPath"),
                Projections.computed("price", new Document("$sum", "$fullPath.price")),
                Projections.computed("departureTimes", new Document("$map", new Document()
                        .append("input", "$fullPath")
                        .append("as", "f")
                        .append("in", new Document("$toDate", "$$f.departureTime"))
                )),
                Projections.computed("arrivalTimes", new Document("$map", new Document()
                        .append("input", "$fullPath")
                        .append("as", "f")
                        .append("in", new Document("$toDate", "$$f.arrivalTime"))))
        )));
        pipeline.add(Aggregates.addFields(new Field<>("fullPath", new Document("$sortArray", new Document()
                .append("input", "$fullPath")
                .append("sortBy", new Document("departureTime", 1))
        ))));
        pipeline.add(Aggregates.addFields(new Field<>("totalTravelTime", new Document("$subtract", List.of(
                new Document("$max", "$arrivalTimes"),
                new Document("$min", "$departureTimes")
        )))));
        pipeline.add(Aggregates.addFields(new Field<>("totalWaitingTime", new Document("$sum", new Document("$map", new Document()
                .append("input", new Document("$range", List.of(0, new Document("$subtract", List.of(
                        new Document("$size", "$fullPath"), 1
                )))))
                .append("as", "i")
                .append("in", new Document("$subtract", List.of(
                        new Document("$toDate", new Document("$arrayElemAt", List.of("$fullPath.arrivalTime", "$$i"))),
                        new Document("$toDate", new Document("$arrayElemAt", List.of("$fullPath.departureTime", new Document("$add", List.of("$$i", 1)))))
                )))
        )))));
        pipeline.add(Aggregates.match(Filters.expr(new Document("$eq", List.of(
                new Document("$last", "$fullPath.arrivalAirportId"),
                destinationAirportId
        )))));
        pipeline.add(Aggregates.sort(Sorts.ascending("totalWaitingTime")));
        pipeline.add(Aggregates.limit(5));

        return MongoUtil.aggregate(getCollection(), pipeline, FlightDto.class);
    }


    public Uni<DeleteResult> deleteById(String id) {
        return MongoUtil.deleteOne(getCollection(), id);
    }


    public Uni<InsertResult> add(FlightEntity flight) {
        return MongoUtil.insertOne(getCollection(), flight);
    }

    public Uni<List<BookStatusFlightDto>> getCapacityAndBookedStatusByFlightNumber(String flightNumber) {
        List<Bson> pipeline = Arrays.asList(
                Aggregates.match(Filters.eq("flightNumber", flightNumber)),
                Aggregates.project(Projections.fields(
                        Projections.include("capacity", "booked"),
                        Projections.excludeId()
                ))
        );

        return MongoUtil.aggregate(getCollectionDto(), pipeline, BookStatusFlightDto.class);
    }


    public Uni<UpdateResult> updateFlightCapacityAndBookedStatus(String flightNumber) {
        Bson filter = Filters.eq("flightNumber", flightNumber);

        return MongoUtil.findOneByFilter(getCollectionDto(), filter)
                .onItem()
                .transformToUni(flight->{
                    Bson update = Updates.inc("capacity",-1);
                    if(flight.getCapacity() == 0){
                                Updates.set("booked", true);
                    }
                    return MongoUtil.updateOne(getCollectionDto(), filter, update);
                });
    }


    public Uni<UpdateResult> incrementCapacity(String flightNumber, ClientSession clientSession) {
        Bson filter = Filters.eq("flightNumber", flightNumber);
        Bson update = Updates.combine(
                Updates.inc("capacity", 1),
                Updates.set("booked", false)
        );

        return MongoUtil.updateOne(getCollection(), filter, update, clientSession);
    }


    public Uni<List<FlightDto>> findOutboundFlights(String departureAirportId, String destinationAirportId, LocalDateTime departureDateTime) {
        Bson filter = Filters.and(
                Filters.eq("departureAirportId", departureAirportId),
                Filters.eq("arrivalAirportId", destinationAirportId),
                Filters.gte("departureTime", departureDateTime)
        );

        return MongoUtil.findManyByFilter(getCollectionFlight(), filter);
    }

    public Uni<List<FlightDto>> findReturnFlights(String departureAirportId, String destinationAirportId, LocalDateTime returnDateTime) {
        Bson filter = Filters.and(
                Filters.eq("departureAirportId", departureAirportId),
                Filters.eq("arrivalAirportId", destinationAirportId),
                Filters.gte("departureTime", returnDateTime)
        );
        return MongoUtil.findManyByFilter(getCollectionFlight(), filter);
    }

    private ReactiveMongoCollection<BookStatusFlightDto> getCollectionDto() {
        return mongoService.getCollection("flights", BookStatusFlightDto.class);
    }

    private ReactiveMongoCollection<FlightDto> getCollectionFlight() {
        return mongoService.getCollection("flights", FlightDto.class);
    }

    private ReactiveMongoCollection<FlightEntity> getCollection() {
        return mongoService.getCollection("flights", FlightEntity.class);
    }

}
