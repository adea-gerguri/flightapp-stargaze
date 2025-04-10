package airline.repository;

import airline.models.AirlineEntity;
import io.quarkus.mongodb.FindOptions;
import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import shared.mongoUtils.InsertResult;

@ApplicationScoped
public class AirlineRepository {

  @Inject ReactiveMongoClient mongoClient;

  public Uni<List<AirlineEntity>> listAirlines() {
    return getCollection().find(new FindOptions()).collect().asList();
  }

  public Uni<InsertResult> addAirline(AirlineEntity airline) {
    return getCollection()
        .insertOne(airline)
        .onItemOrFailure()
        .transformToUni(
            (item, failure) -> {
              if (failure != null || item.getInsertedId() == null) {
                return Uni.createFrom().failure(new RuntimeException("Airline was not saved!"));
              }
              return Uni.createFrom().item(InsertResult.fromId(item.getInsertedId().toString()));
            });
  }

  private ReactiveMongoCollection<AirlineEntity> getCollection() {
    return mongoClient.getDatabase("stargaze").getCollection("airlines", AirlineEntity.class);
  }
}
