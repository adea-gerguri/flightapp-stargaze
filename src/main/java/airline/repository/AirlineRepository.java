package airline.repository;

import airline.models.AirlineEntity;
import airline.models.dto.AirlineDto;
import airline.models.dto.AirlinesByCityDto;
import airline.models.dto.AirlinesByCountryDto;
import airline.models.dto.CreateAirlineDTO;
import airport.models.dto.AirportGroupByCountryDto;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import io.quarkus.mongodb.FindOptions;
import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import org.bson.conversions.Bson;
import shared.PaginationQueryParams;
import shared.mongoUtils.DeleteResult;
import shared.mongoUtils.InsertResult;
import shared.mongoUtils.MongoUtil;
import shared.mongoUtils.UpdateResult;
import ticket.models.TicketEntity;

@ApplicationScoped
public class AirlineRepository {

  @Inject
  MongoUtil mongoService;

  public Uni<InsertResult> addAirline(AirlineEntity airline) {
    return MongoUtil.insertOne(getCollection(), airline);
  }

  public Uni<DeleteResult> deleteAirline(String id){
    return MongoUtil.deleteOne(getCollection(), id);
  }

  public Uni<List<AirlinesByCityDto>> groupByCity(PaginationQueryParams paginationQueryParams) {
    List<Bson> pipeline = new ArrayList<>();
    pipeline.add(Aggregates.group("$city", Accumulators.sum("planeCount", "$planeCount")));
    pipeline.add(Aggregates.project(Projections.fields(
            Projections.computed("city", "$_id"),
            Projections.include("planeCount")
    )));

    return MongoUtil.aggregate(getCollection(), pipeline, paginationQueryParams, AirlinesByCityDto.class);
  }

  public Uni<List<AirlinesByCountryDto>> groupAirlinesByCountry(PaginationQueryParams paginationQueryParams) {
    List<Bson> pipeline = new ArrayList<>();
    pipeline.add(Aggregates.group("$country", Accumulators.sum("planeCount", "$planeCount")));
    pipeline.add(Aggregates.project(Projections.fields(
            Projections.computed("country", "$_id"),
            Projections.include("planeCount")
    )));

    return MongoUtil.aggregate(getCollection(), pipeline, paginationQueryParams, AirlinesByCountryDto.class);
  }


  private ReactiveMongoCollection<AirlineEntity> getCollection() {
    return mongoService.getCollection("airlines", AirlineEntity.class);
  }






}
