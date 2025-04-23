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
import shared.PaginationQueryParams;
import shared.mongoUtils.InsertResult;
import shared.mongoUtils.MongoUtil;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class BaggageRepository {
    @Inject
    MongoUtil mongoService;

    public Uni<InsertResult> addBaggage(BaggageEntity baggage){
       return MongoUtil.insertOne(getCollection(), baggage);
    }

    public Uni<List<BaggageWeightDto>> groupByBaggageType(PaginationQueryParams paginationQueryParams) {
        List<Bson> pipeline = new ArrayList<>();
        pipeline.add(Aggregates.group("$baggageType",
                        Accumulators.sum("totalWeight","$weight"),
                        Accumulators.sum("count",1)
                ));
        pipeline.add(Aggregates.project(Projections.fields(
                Projections.computed("baggageType","$_id"),
                Projections.include("totalWeight", "count")
        )));
        pipeline.addAll(MongoUtil.listWithPagination(paginationQueryParams, "totalWeight"));
        return MongoUtil.aggregate(getCollection(), pipeline, BaggageWeightDto.class);
    }

    public Uni<List<BaggagePriceDto>> groupByReservationIdAndTotalPrice(PaginationQueryParams paginationQueryParams) {
        List<Bson> pipeline = new ArrayList<>();
        pipeline.add(Aggregates.group("$reservationId",
                Accumulators.sum("totalPrice", "$price"),
                Accumulators.sum("count",1)
                ));
        pipeline.add(Aggregates.project(Projections.fields(
                Projections.fields(
                        Projections.computed("baggageType","$_id"),
                        Projections.include("totalPrice", "count")
                )
        )));
        pipeline.addAll(MongoUtil.listWithPagination(paginationQueryParams, "totalPrice"));

        return MongoUtil.aggregate(getCollection(), pipeline, BaggagePriceDto.class);
    }


    private ReactiveMongoCollection<BaggageEntity> getCollection(){
        return mongoService.getCollection("baggages", BaggageEntity.class);
    }



}
