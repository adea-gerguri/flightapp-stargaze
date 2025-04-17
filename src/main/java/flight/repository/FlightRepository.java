package flight.repository;

import com.mongodb.client.model.*;
import flight.mappers.FlightMapper;
import flight.models.dto.CreateFlightDto;
import flight.models.dto.FlightDto;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import flight.models.FlightEntity;
import org.bson.Document;
import org.bson.conversions.Bson;
import shared.mongoUtils.DeleteResult;
import shared.mongoUtils.InsertResult;
import shared.mongoUtils.MongoUtil;
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

    public Uni<List<FlightDto>> findFastestRoute(String destinationAirportId, String departureDate, String departureAirportId) {
        List<Bson> pipeline = List.of(
                Aggregates.match(Filters.and(
                        Filters.eq("arrivalAirportId", destinationAirportId),
                        Filters.eq("departureAirportId", departureAirportId),
                        Filters.regex("departureTime", departureDate)
                )),
                Aggregates.project(Document.parse("{ " +
                        "'flightNumber': 1, " +
                        "'departureAirportId': 1, " +
                        "'arrivalAirportId': 1, " +
                        "'departureTime': 1, " +
                        "'arrivalTime': 1, " +
                        "'duration': { $subtract: [ { $toDate: '$arrivalTime' }, { $toDate: '$departureTime' } ] } " +
                        "}")),
                Aggregates.sort(Sorts.ascending("duration")),
                Aggregates.limit(1)
        );

        return MongoUtil.aggregate(getCollection(), pipeline, FlightDto.class);
    }

    public Uni<List<FlightDto>> findCheapestRoute(String destinationAirportId, String departureDate, String departureAirportId) {
        List<Bson> pipeline = List.of(
                Aggregates.match(Filters.and(
                        Filters.eq("arrivalAirportId", destinationAirportId),
                        Filters.eq("departureAirportId", departureAirportId),
                        Filters.regex("departureTime", departureDate)
                )),
                Aggregates.project(Document.parse("{ " +
                        "'flightNumber': 1, " +
                        "'departureAirportId': 1, " +
                        "'arrivalAirportId': 1, " +
                        "'departureTime': 1, " +
                        "'arrivalTime': 1, " +
                        "'price':1,"+
                        "'duration': { $subtract: [ { $toDate: '$arrivalTime' }, { $toDate: '$departureTime' } ] } " +
                        "}")),
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
                Aggregates.project(Document.parse("{ " +
                        "'flightNumber': 1, " +
                        "'departureAirportId': 1, " +
                        "'arrivalAirportId': 1, " +
                        "'departureTime': 1, " +
                        "'arrivalTime': 1, " +
                        "'price':1,"+
                        "'duration': { $subtract: [ { $toDate: '$arrivalTime' }, { $toDate: '$departureTime' } ] } " +
                        "}")),
                Aggregates.sort(Sorts.descending("price")),
                Aggregates.limit(1)
        );

        return MongoUtil.aggregate(getCollection(), pipeline, FlightDto.class);
    }

    public Uni<List<FlightDto>> findFlightsWithStopsAndWaitingTimes(String departureAirportId, String destinationAirportId, String departureDate) {
        List<Bson> pipeline = List.of(
                Aggregates.match(Filters.and(
                        Filters.eq("departureAirportId", departureAirportId),
                        Filters.eq("arrivalAirportId", destinationAirportId),
                        Filters.regex("departureTime", departureDate)
                )),
                Aggregates.graphLookup("flights",
                        "$arrivalAirportId",
                        "departureAirportId",
                        "arrivalAirportId",
                        "connections",
                        new GraphLookupOptions()
                                .maxDepth(2)
                                .restrictSearchWithMatch(Filters.regex("departureTime", departureDate))
                ),
                Aggregates.project(Document.parse("""
                    {
                        fullPath: {
                            $concatArrays: [[
                                {
                                    departureAirportId: "$departureAirportId",
                                    arrivalAirportId: "$arrivalAirportId",
                                    departureTime: "$departureTime",
                                    arrivalTime: "$arrivalTime",
                                    price: "$price"
                                }
                            ], "$connections"]
                        }
                    }
                    """)),
                Aggregates.project(Document.parse("""
                    {
                        fullPath: 1,
                        price: { $sum: "$fullPath.price" },
                        departureTimes: {
                            $map: {
                                input: "$fullPath",
                                as: "f",
                                in: { $toDate: "$$f.departureTime" }
                            }
                        },
                        arrivalTimes: {
                            $map: {
                                input: "$fullPath",
                                as: "f",
                                in: { $toDate: "$$f.arrivalTime" }
                            }
                        }
                    }
                    """)),
                Aggregates.addFields(new Field<>("fullPath", Document.parse("""
                        {
                            $sortArray: {
                            input: "$fullPath",
                            sortBy: { departureTime: 1 }
                           }
                        }
                """))),
                Aggregates.addFields(new Field<>("totalTravelTime",
                        Document.parse("""
                        {
                            $subtract: [
                                { $max: "$arrivalTimes" },
                                { $min: "$departureTimes" }
                            ]
                        }
                        """)
                )),
                Aggregates.addFields(new Field<>("totalWaitingTime",
                        Document.parse("""
                        {
                            "$sum": {
                                "$map": {
                                    "input": {
                                        "$range": [0, { "$subtract": [ { "$size": "$fullPath" }, 1 ] }]
                                    },
                                    "as": "i",
                                    "in": {
                                        "$subtract": [
                                            {
                                                "$toDate": {
                                                    "$arrayElemAt": ["$fullPath.arrivalTime", "$$i"]
                                                }
                                            },
                                            {
                                                "$toDate": {
                                                    "$arrayElemAt": ["$fullPath.departureTime", { "$add": ["$$i", 1] }]
                                                }
                                            }
                                        ]
                                    }
                                }
                            }
                        }
                        """)
                )),
                Aggregates.match(Filters.expr(new Document("$eq", List.of(
                        new Document("$last", "$fullPath.arrivalAirportId"),
                        destinationAirportId
                )))),
                Aggregates.sort(Sorts.ascending("totalWaitingTime")),
                Aggregates.limit(5)
        );

        return MongoUtil.aggregate(getCollection(), pipeline, FlightDto.class);
    }



    public Uni<DeleteResult> deleteById(String id) {
        return MongoUtil.deleteOne(getCollection(), id);
    }


    public Uni<InsertResult> add(FlightEntity flight){
        return MongoUtil.insertOne(getCollection(), flight);
    }


    private ReactiveMongoCollection<FlightEntity> getCollection(){
        return mongoService.getCollection("flights", FlightEntity.class);
    }

}
