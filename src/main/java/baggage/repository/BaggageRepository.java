package baggage.repository;

import baggage.models.dto.BaggagePriceDto;
import baggage.models.dto.BaggageWeightDto;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Projections;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import baggage.models.BaggageEntity;
import org.bson.conversions.Bson;
import shared.mongoUtils.InsertResult;
import shared.mongoUtils.MongoUtil;

import java.util.List;

@ApplicationScoped
public class BaggageRepository {
    @Inject
    MongoUtil mongoService;


    public Uni<InsertResult> addBaggage(BaggageEntity baggage){
       return MongoUtil.insertOne(getCollection(), baggage);
    }

    public Uni<List<BaggageWeightDto>> groupByBaggageType() {
        List<Bson> pipeline = List.of(
                Aggregates.group("$baggageType",
                        Accumulators.sum("totalWeight", "$weight"),
                        Accumulators.sum("count", 1)
                ),
                Aggregates.project(Projections.fields(
                        Projections.computed("baggageType", "$_id"),
                        Projections.include("totalWeight", "count")
                ))
        );

        return MongoUtil.aggregate(getCollection(), pipeline, BaggageWeightDto.class);
    }

    public Uni<List<BaggagePriceDto>> groupByReservationIdAndTotalPrice() {
        List<Bson> pipeline = List.of(
                Aggregates.group("$reservationId",
                        Accumulators.sum("totalPrice", "$price"),
                        Accumulators.sum("count", 1)
                ),
                Aggregates.project(Projections.fields(
                        Projections.computed("reservationId", "$_id"),
                        Projections.include("totalPrice", "count")
                ))
        );
        return MongoUtil.aggregate(mongoService.getCollection("baggages", BaggageEntity.class), pipeline, BaggagePriceDto.class);
    }


    private ReactiveMongoCollection<BaggageEntity> getCollection(){
        return mongoService.getCollection("baggages", BaggageEntity.class);
    }



}
