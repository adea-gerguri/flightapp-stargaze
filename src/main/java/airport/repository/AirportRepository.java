package airport.repository;

import airline.models.dto.AirlinesByCountryDto;
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
import shared.PaginationQueryParams;
import shared.mongoUtils.DeleteResult;
import shared.mongoUtils.InsertResult;
import shared.mongoUtils.MongoUtil;
import shared.mongoUtils.UpdateResult;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AirportRepository {
    @Inject
    MongoUtil mongoUtil;


    public Uni<List<AirportEntity>> listAll(){
        return MongoUtil.listAll(getCollection(),new FindOptions());
    }

    public Uni<InsertResult> add(AirportEntity airport){
        return MongoUtil.insertOne(getCollection(), airport);
    }

    public Uni<DeleteResult> delete(String id) {
        return MongoUtil.deleteOne(getCollection(), id);
    }


    public Uni<List<AirportGroupByCountryDto>> groupAirportsByCountry(PaginationQueryParams paginationQueryParams) {
        List<Bson> pipeline = new ArrayList<>();
        pipeline.add(Aggregates.group("$country", Accumulators.sum("airportCount", "$airportCount")));
        pipeline.add(Aggregates.project(Projections.fields(
                Projections.computed("country", "$_id"),
                Projections.include("airportCount")
        )));

        pipeline.addAll(MongoUtil.listWithPagination(paginationQueryParams, "airportCount"));

        return MongoUtil.aggregate(getCollection(), pipeline, AirportGroupByCountryDto.class);
    }

    public Uni<List<AirportGroupByCityDto>> groupAirportsByCity(PaginationQueryParams paginationQueryParams) {
        List<Bson> pipeline = new ArrayList<>();
        pipeline.add(Aggregates.group("$city", Accumulators.sum("airportCount", "$airportCount")));
        pipeline.add(Aggregates.project(Projections.fields(
                Projections.computed("city", "$_id"),
                Projections.include("airportCount")
        )));

        pipeline.addAll(MongoUtil.listWithPagination(paginationQueryParams, "airportCount"));

        return MongoUtil.aggregate(getCollection(), pipeline, AirportGroupByCityDto.class);
    }


    private ReactiveMongoCollection<AirportEntity> getCollection() {
        return mongoUtil.getCollection("airports", AirportEntity.class);
    }
}
