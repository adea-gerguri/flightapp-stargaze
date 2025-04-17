package airport.repository;

import airport.models.dto.AirportGroupByCityDto;
import airport.models.dto.AirportGroupByCountryDto;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import io.quarkus.mongodb.FindOptions;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import airport.models.AirportEntity;
import org.bson.Document;
import org.bson.conversions.Bson;
import shared.mongoUtils.DeleteResult;
import shared.mongoUtils.InsertResult;
import shared.mongoUtils.MongoUtil;
import shared.mongoUtils.UpdateResult;

import java.util.List;

@ApplicationScoped
public class AirportRepository {
    @Inject
    MongoUtil mongoUtil;

    private Bson sorting;

    public Uni<List<AirportEntity>> listAll(){
        return MongoUtil.listAll(getCollection(),new FindOptions());
    }

    public Uni<InsertResult> add(AirportEntity airport){
        return MongoUtil.insertOne(getCollection(), airport);
    }

    public Uni<DeleteResult> delete(String id) {
        return MongoUtil.deleteOne(getCollection(), id);
    }


    public Uni<List<AirportGroupByCountryDto>> groupAirportsByCountry(int skip, int limit, int sort) {
        if(sort ==1){
            sorting= Aggregates.sort(Sorts.ascending("airportCount"));
        }else{
            sorting = Aggregates.sort(Sorts.descending("airportCount"));
        }
        List<Bson> pipeline = List.of(
                Aggregates.group("$country", Accumulators.sum("airportCount", 1)),
                Aggregates.project(Projections.fields(
                        Projections.computed("country", "$_id"),
                        Projections.include("airportCount")
                )),
                Aggregates.sort(sorting),
                Aggregates.skip(skip),
                Aggregates.limit(limit)
        );

        return MongoUtil.aggregate(getCollection(), pipeline, AirportGroupByCountryDto.class);
    }

    public Uni<List<AirportGroupByCityDto>> groupAirportsByCity(int skip, int limit, int sort){
        if(sort ==1){
            sorting= Aggregates.sort(Sorts.ascending("airportCount"));
        }else{
            sorting = Aggregates.sort(Sorts.descending("airportCount"));
        }
        List<Bson> pipeline = List.of(
                Aggregates.group("$city", Accumulators.sum("airportCount",1)),
                Aggregates.project(Projections.fields(
                        Projections.computed("city", "$_id"),
                        Projections.include("airportCount")
                )),
                Aggregates.sort(sorting),
                Aggregates.skip(skip),
                Aggregates.limit(limit)
        );
        return MongoUtil.aggregate(getCollection(), pipeline, AirportGroupByCityDto.class);
    }


    private ReactiveMongoCollection<AirportEntity> getCollection() {
        return mongoUtil.getCollection("airports", AirportEntity.class);
    }
}
