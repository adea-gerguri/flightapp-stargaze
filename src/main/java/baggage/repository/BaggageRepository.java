package baggage.repository;

import baggage.models.dto.BaggagePriceDto;
import baggage.models.dto.BaggageWeightDto;
import baggage.models.dto.CreateBaggageDto;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
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

    private Bson sorting;

    public Uni<InsertResult> addBaggage(BaggageEntity baggage){
       return MongoUtil.insertOne(getCollection(), baggage);
    }

    public Uni<List<BaggageWeightDto>> groupByBaggageType(int skip, int limit, int sort) {
        if(sort==1){
            sorting = Sorts.ascending("totalWeight");
        }else{
            sorting = Sorts.descending("totalWeight");
        }
        List<Bson> pipeline = List.of(
                Aggregates.group("$baggageType",
                        Accumulators.sum("totalWeight", "$weight"),
                        Accumulators.sum("count", 1)
                ),
                Aggregates.project(Projections.fields(
                        Projections.computed("baggageType", "$_id"),
                        Projections.include("totalWeight", "count")
                )),
                Aggregates.sort(sorting),
                Aggregates.skip(skip),
                Aggregates.limit(limit)
        );

        return MongoUtil.aggregate(getCollection(), pipeline, BaggageWeightDto.class);
    }

    public Uni<List<BaggagePriceDto>> groupByReservationIdAndTotalPrice(int skip, int limit, int sort) {
        if(sort ==1){
            sorting = Sorts.ascending("totalPrice");
        } else{
            sorting = Sorts.descending("totalPrice");
        }
        List<Bson> pipeline = List.of(
                Aggregates.group("$reservationId",
                        Accumulators.sum("totalPrice", "$price"),
                        Accumulators.sum("count", 1)
                ),
                Aggregates.project(Projections.fields(
                        Projections.computed("baggageType", "$_id"),
                        Projections.include("totalPrice", "count")
                )),
                Aggregates.sort(sorting),
                Aggregates.skip(skip),
                Aggregates.limit(limit)
        );
        return MongoUtil.aggregate(mongoService.getCollection("baggages", BaggageEntity.class), pipeline, BaggagePriceDto.class);
    }


    private ReactiveMongoCollection<BaggageEntity> getCollection(){
        return mongoService.getCollection("baggages", BaggageEntity.class);
    }



}
