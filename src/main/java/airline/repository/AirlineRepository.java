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
import java.util.List;

import org.bson.conversions.Bson;
import shared.mongoUtils.DeleteResult;
import shared.mongoUtils.InsertResult;
import shared.mongoUtils.MongoUtil;
import shared.mongoUtils.UpdateResult;
import ticket.models.TicketEntity;

@ApplicationScoped
public class AirlineRepository {

  @Inject
  MongoUtil mongoService;

  private Bson sorting;

  public Uni<InsertResult> addAirline(AirlineEntity airline) {
    return MongoUtil.insertOne(getCollection(), airline);
  }

  public Uni<DeleteResult> deleteAirline(String id){
    return MongoUtil.deleteOne(getCollection(), id);
  }

  public Uni<List<AirlinesByCityDto>> groupByCity(int skip, int limit, int sort){
    if(sort==1){
      sorting = Sorts.ascending("planeCount");
    } else{
      sorting = Sorts.descending("planeCount");
    }
    List<Bson>pipeline = List.of(
            Aggregates.group("$city",Accumulators.sum("planeCount","$planeCount")),
            Aggregates.project(Projections.fields(
                    Projections.computed("city","$_id"),
                    Projections.include("planeCount")
            )),
            Aggregates.sort(sorting),
            Aggregates.skip(skip),
            Aggregates.limit(limit)
    );
    return MongoUtil.aggregate(getCollection(), pipeline, AirlinesByCityDto.class);
  }


  public Uni<List<AirlinesByCountryDto>> groupAirlinesByCountry(int skip, int limit, int sort) {
    if(sort==1){
      sorting = Sorts.ascending("planeCount");
    }else{
      sorting = Sorts.descending("planeCount");
    }
    List<Bson> pipeline = List.of(
            Aggregates.group("$country", Accumulators.sum("planeCount", "$planeCount")),
            Aggregates.project(Projections.fields(
                    Projections.computed("country", "$_id"),
                    Projections.include("planeCount")
            )),
            Aggregates.sort(sorting),
            Aggregates.skip(skip),
            Aggregates.limit(limit)
    );

    return MongoUtil.aggregate(getCollection(), pipeline, AirlinesByCountryDto.class);
  }


  private ReactiveMongoCollection<AirlineEntity> getCollection() {
    return mongoService.getCollection("airlines", AirlineEntity.class);
  }






}
