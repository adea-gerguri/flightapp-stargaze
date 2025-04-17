package shared.mongoUtils;

import com.mongodb.client.model.Filters;
import io.quarkus.mongodb.FindOptions;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.quarkus.runtime.ExecutorRecorder;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.hibernate.validator.spi.messageinterpolation.LocaleResolver;

import java.util.List;

import static com.mongodb.client.model.Sorts.ascending;


@ApplicationScoped
public class MongoUtil {
    @Inject
    MongoDB mongoDB;


    public <T> ReactiveMongoCollection<T> getCollection(String collection, Class<T> type){
        return mongoDB.getDatabase().getCollection(collection, type);
    }

    public static <T> Uni<List<T>> listAll(ReactiveMongoCollection<T> collection, FindOptions options) {
        return collection
                .find(options)
                .collect()
                .asList();
    }

    public static <T> Uni<T> findOne(ReactiveMongoCollection<T> collection, FindOptions options) {
        options.limit(1);

        return collection
                .find(options)
                .emitOn(ExecutorRecorder.getCurrent())
                .toUni()
                .onItem()
                .ifNull()
                .failWith(new NotFoundException());
    }

    public static <T> Uni<InsertResult> insertOne(ReactiveMongoCollection<T> collection, T entity) {
        return collection.insertOne(entity)
                .onItemOrFailure()
                .transformToUni((item, failure) -> {
                    if (failure != null) {
                        return Uni.createFrom().failure(new RuntimeException("Entity was not saved!"));
                    }
                    return Uni.createFrom().item(InsertResult.fromId(item.getInsertedId().toString()));
                });
    }

    public static <T> Uni<UpdateResult> updateOne(ReactiveMongoCollection<T> collection, String id, Document update) {
        Document filter = new Document("_id", id);
        return collection.updateOne(filter, new Document("$set", update))
                .onItem()
                .transform(mongoResult -> {
                    if (mongoResult.getMatchedCount() == 0) {
                        throw new NotFoundException("Document not found for update");
                    }
                    return UpdateResult.fromCounts(
                            mongoResult.getMatchedCount(),
                            mongoResult.getModifiedCount()
                    );
                });
    }


    public static <T> Uni<DeleteResult> deleteOne(ReactiveMongoCollection<T> collection, String id) {
        Bson filter = Filters.eq("_id", new ObjectId(id));
        return collection.deleteOne(filter)
                .onItem()
                .transform(mongoResult -> {
                    if (mongoResult.getDeletedCount() == 0) {
                        throw new NotFoundException("Document not found for deletion");
                    }
                    return DeleteResult.fromCount((int) mongoResult.getDeletedCount());
                });
    }

    public static <T> Uni<Long> count(ReactiveMongoCollection<T> collection, Bson filter) {
        return collection.countDocuments(filter);
    }

    public static <T> Uni<List<T>> paginateAscending(ReactiveMongoCollection<T> collection, int skip, int limit, String field) {
        return collection.find(new FindOptions().sort(ascending(field))
                .skip(skip)
                .limit(limit))
                .collect().asList();
    }



    public static <T> Uni<UpdateResult> updateMany(ReactiveMongoCollection<T> collection, Bson filter, Bson update) {
        return collection.updateMany(filter, update)
                .onItem()
                .transform(mongoResult -> UpdateResult.fromCounts(
                         mongoResult.getMatchedCount(),
                        mongoResult.getModifiedCount()
                ));
    }

    public static <T> Uni<DeleteResult> deleteMany(ReactiveMongoCollection<T> collection, Bson filter) {
        return collection.deleteMany(filter)
                .onItem()
                .transform(mongoResult -> DeleteResult.fromCount((int) mongoResult.getDeletedCount()));
    }


    public static <T> Uni<InsertResult> replaceOne(ReactiveMongoCollection<T> collection, String id, T entity){
        Bson filter = Filters.eq("_id", id);
        return collection.replaceOne(filter,entity)
                .onItem()
                .transformToUni(item->Uni.createFrom().item(InsertResult.fromId(id)))
                .onFailure()
                .recoverWithItem(failure->null);
    }


    public static <T> Uni<T> findUpdate(ReactiveMongoCollection<T> collection, Bson filter, Bson update, Class<T> type) {
        return collection.findOneAndUpdate(filter, update)
                .onItem()
                .transformToUni(item -> Uni.createFrom().item(item))
                .onFailure()
                .recoverWithItem(failure -> null);
    }

    public static <T> Uni<List<T>> distinct(ReactiveMongoCollection<T> collection, String field, Bson filter, Class<T> type) {
        return collection.distinct(field, filter, type)
                .collect()
                .asList();
    }

    public static <T> Uni<List<T>> aggregate(ReactiveMongoCollection<?> collection, List<Bson> pipeline, Class<T> outputClass) {
        return collection
                .withDocumentClass(outputClass)
                .aggregate(pipeline, outputClass)
                .collect()
                .asList();
    }
}
