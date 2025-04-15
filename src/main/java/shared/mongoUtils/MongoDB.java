package shared.mongoUtils;

import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.quarkus.mongodb.reactive.ReactiveMongoDatabase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class MongoDB {
    @Inject
    ReactiveMongoClient mongoClient;

    public ReactiveMongoDatabase getDatabase() {
        return mongoClient.getDatabase("stargaze");
    }
}
