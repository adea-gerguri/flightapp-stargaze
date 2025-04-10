package repository;

import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import model.dto.AirportDto;
import model.mapper.AirportMapper;
import model.mapper.MapperService;
import model.view.Airport;
import org.bson.Document;

import java.util.List;

@ApplicationScoped
public class AirportRepository {
    @Inject
    AirportMapper airportMapper;

    @Inject
    ReactiveMongoClient mongoClient;

    @Inject
    MapperService mapper;

    public Uni<List<AirportDto>> listAirports(){
        return getCollection().find()
                .map(document ->{
                    Airport airport = new Airport();
                    airport.setAirlineName(document.getString("airlineName"));
                    airport.setCity(document.getString("city"));
                    airport.setCountry(document.getString("country"));
                    airport.setName(document.getString("name"));
                    airport.setCode(document.getString("code"));
                    return mapper.map(airport, AirportDto.class);
                }).collect().asList();
    }

    public Uni<Void> addAirport(Airport airport){
        Document document = new Document()
                .append("name", airport.getName())
                .append("code", airport.getCode())
                .append("city", airport.getCity())
                .append("country", airport.getCountry())
                .append("airlineName", airport.getAirlineName());
        return getCollection().insertOne(document)
                .onItem().ignore().andContinueWithNull();

    }

    private ReactiveMongoCollection<Document> getCollection(){
        return mongoClient.getDatabase("stargaze").getCollection("airports");
    }
}
