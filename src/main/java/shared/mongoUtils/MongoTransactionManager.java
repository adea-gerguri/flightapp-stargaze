package shared.mongoUtils;
import com.mongodb.TransactionOptions;
import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.mongodb.reactivestreams.client.ClientSession;
import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import io.smallrye.mutiny.Uni;
import org.reactivestreams.FlowAdapters;


@ApplicationScoped
public class MongoTransactionManager {
    @Inject
    MongoDB mongoDB;

    public Uni<ClientSession> execute() {
        return mongoDB.mongoClient.startSession().invoke(ClientSession::startTransaction);
    }

    public Uni<Void> commit(Uni<ClientSession> sessionUni) {
        return sessionUni
                .call(session -> Uni.createFrom().publisher(FlowAdapters.toFlowPublisher(session.commitTransaction())))
                .replaceWithVoid();
    }

    public Uni<Void> end(Uni<ClientSession> sessionUni) {
        return sessionUni
                .call(session -> Uni.createFrom().publisher(FlowAdapters.toFlowPublisher(session.abortTransaction())))
                .replaceWithVoid();
    }
}