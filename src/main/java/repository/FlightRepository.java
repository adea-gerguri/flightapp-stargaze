package repository;

import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import model.dto.FlightDto;
import model.mapper.FlightMapper;
import model.mapper.MapperService;
import model.view.Flight;
import org.bson.Document;


import java.util.List;

@ApplicationScoped
public class FlightRepository {

    @Inject
    ReactiveMongoClient mongoClient;

    @Inject
    MapperService mapper;

    public Uni<List<FlightDto>> listFlights(){
        return getCollection().find()
                .map(document -> {
                    Flight flight = new Flight();
                    flight.setFlightNumber(document.getString("flightNumber"));
                    flight.setAirline(document.getString("airline"));
                    flight.setBooked(document.getBoolean("booked"));
                    flight.setPrice(document.getDouble("price"));
                    flight.setCapacity(document.getInteger(("capacity")));
                    flight.setDepartureTime(document.getString("departureTime"));
                    flight.setArrivalTime(document.getString("arrivalTime"));
//                    flight.setFlightType(FlightType.valueOf(document.getString("flightType")));
//                    flight.setStatus(Status.valueOf(document.getString("status")));
                    return mapper.map(flight, FlightDto.class);
                }).collect().asList();
    }
    public Uni<Void> addFlight(Flight flight){
        Document document = new Document()
                .append("airline", flight.getAirline())
                .append("departureTime", flight.getDepartureTime())
                .append("arrivalTime", flight.getArrivalTime())
                .append("flightNumber", flight.getFlightNumber())
                .append("price",flight.getPrice())
                .append("booked", flight.getBooked())
                .append("capacity", flight.getCapacity());
//                .append("flightType", flight.getFlightType())
        //        .append("status", flight.getStatus())
        return getCollection().insertOne(document)
                .onItem().ignore().andContinueWithNull();
    }

    private ReactiveMongoCollection<Document> getCollection(){
        return mongoClient.getDatabase("stargaze").getCollection("flights");
    }
}
