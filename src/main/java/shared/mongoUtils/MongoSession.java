package shared.mongoUtils;

import com.mongodb.reactivestreams.client.ClientSession;
import io.smallrye.mutiny.Uni;

public class MongoSession {
    private ClientSession clientSession;

    public void setSession(ClientSession clientSession) {
        this.clientSession = clientSession;
    }

    public ClientSession getSession() {
        return clientSession;
    }

    public Uni<ClientSession> getSessionUni() {
        return Uni.createFrom().item(clientSession);
    }
}
