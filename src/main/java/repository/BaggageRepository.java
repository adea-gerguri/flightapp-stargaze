package repository;

import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import model.dto.BaggageDto;
import model.mapper.BaggageMapper;
import model.mapper.MapperService;
import model.view.Baggage;
import org.bson.Document;

import java.util.List;

@ApplicationScoped
public class BaggageRepository {
    @Inject
    ReactiveMongoClient mongoClient;

    @Inject
    MapperService mapper;

    public Uni<List<BaggageDto>> listBaggages() {
        return getCollection().find()
                .map(document ->{
                    Baggage baggage = new Baggage();
//                    baggage.setBaggageType(BaggageType.valueOf(document.getString("baggageType")));
                    baggage.setHeight(document.getDouble("height"));
                    baggage.setLength(document.getDouble("length"));
                    baggage.setWidth(document.getDouble("width"));
                    baggage.setWeight(document.getDouble("weight"));
                    baggage.setPrice(document.getDouble("price"));
                    return mapper.map(baggage, BaggageDto.class);
                }).collect().asList();
    }
    public Uni<Void> addBaggage(Baggage baggage){
        Document document = new Document()
                .append("baggageType", baggage.getBaggageType())
                .append("height", baggage.getHeight())
                .append("length", baggage.getLength())
                .append("width", baggage.getLength())
                .append("weight", baggage.getWeight())
                .append("price", baggage.getPrice());
        return getCollection().insertOne(document)
                .onItem().ignore().andContinueWithNull();
    }

    private ReactiveMongoCollection<Document> getCollection(){
        return mongoClient.getDatabase("stargaze").getCollection("baggages");
    }

}
