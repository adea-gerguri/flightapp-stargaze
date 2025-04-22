package user.repository;

import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Updates;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.conversions.Bson;
import shared.mongoUtils.DeleteResult;
import shared.mongoUtils.InsertResult;
import shared.mongoUtils.MongoUtil;
import shared.mongoUtils.UpdateResult;
import user.exceptions.UserException;
import user.models.dto.UserDto;
import user.models.UserEntity;


import java.util.List;

import static com.mongodb.client.model.Sorts.ascending;

@ApplicationScoped
public class UserRepository {
    @Inject
    MongoUtil mongoService;

    public Uni<List<UserDto>> listUsers(int skip, int limit) {
        List<Bson> pipeline = List.of(
                Aggregates.sort(ascending("name")),
                Aggregates.skip(skip),
                Aggregates.limit(limit),
                Aggregates.project(Projections.fields(
                        Projections.include("id", "name", "email", "phoneNumber")
                ))
        );

        return MongoUtil.aggregate(getCollection(), pipeline, UserDto.class);
    }


    public Uni<InsertResult> addUser(UserEntity airline) {
       return MongoUtil.insertOne(getCollection(), airline);
    }

    public Uni<DeleteResult> deleteUser(String id) {
        return MongoUtil.deleteOne(getCollection(), id);
    }


    public Uni<UpdateResult> decreaseBalance(String userId, double price) {
        Bson filter = Filters.eq("id", userId);
        Bson update = Updates.inc("balance", -price);

        return getCollection()
                .updateOne(filter, update)
                .onItem().transform(updateResult -> {
                    if (updateResult.getModifiedCount() > 0) {
                        return new shared.mongoUtils.UpdateResult(updateResult.getMatchedCount(), updateResult.getModifiedCount());
                    } else {
                        throw new UserException("Failed to update user balance", 500);
                    }
                });
    }

    public Uni<UpdateResult> increaseBalance(String userId, double price) {
        Bson filter = Filters.eq("id", userId);
        Bson update = Updates.inc("balance", price);

        return getCollection()
                .updateOne(filter, update)
                .onItem().transform(updateResult -> {
                    if (updateResult.getModifiedCount() > 0) {
                        return new shared.mongoUtils.UpdateResult(updateResult.getMatchedCount(), updateResult.getModifiedCount());
                    } else {
                        throw new UserException("Failed to update user balance", 500);
                    }
                });
    }


    private ReactiveMongoCollection<UserEntity> getCollection() {
        return mongoService.getCollection("users",UserEntity.class);
    }





}
