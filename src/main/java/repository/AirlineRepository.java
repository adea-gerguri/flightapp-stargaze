package repository;

import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import model.dto.AirlineDto;
import model.mapper.AirlineMapper;
import model.mapper.MapperService;
import model.view.Airline;
import org.bson.Document;

import java.util.List;

@ApplicationScoped
public class AirlineRepository {

    @Inject
    ReactiveMongoClient mongoClient;

    @Inject
    MapperService mapper;

    public Uni<List<AirlineDto>> listAirlines(){
        return getCollection().find()
                .map(document->{
                    Airline airline = new Airline();
                    airline.setName(document.getString("airlineName"));
                    airline.setCode(document.getString("code"));
                    airline.setCountry(document.getString("country"));
                    airline.setPlaneCount(document.getInteger("planeCount"));
                    return mapper.map(airline, AirlineDto.class);
                }).collect().asList();
    }
    public Uni<Void> addAirline(Airline airline){
        Document document = new Document()
                .append("airlineName", airline.getName())
                .append("code", airline.getCode())
                .append("country", airline.getCountry())
                .append("planeCount", airline.getPlaneCount());
        return getCollection().insertOne(document)
                .onItem().ignore().andContinueWithNull();
    }

    private ReactiveMongoCollection<Document> getCollection(){
        return mongoClient.getDatabase("stargaze").getCollection("airlines");
    }

}
