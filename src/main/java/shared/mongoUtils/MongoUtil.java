package shared.mongoUtils;

import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.reactivestreams.client.ClientSession;
import io.quarkus.mongodb.FindOptions;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.quarkus.runtime.ExecutorRecorder;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.client.Client;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.spi.messageinterpolation.LocaleResolver;
import shared.PaginationQueryParams;
import shared.exceptions.DocumentNotFound;

import java.util.ArrayList;
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
                .failWith(new DocumentNotFound("Document not found",404));
    }

    private static List<Bson> listWithPagination(PaginationQueryParams paginationQueryParams) {
        List<Bson> pipeline = new ArrayList<>();

        if (paginationQueryParams.getSort() == 1) {
            pipeline.add(Aggregates.sort(Sorts.ascending(paginationQueryParams.getSortField())));
        } else {
            pipeline.add(Aggregates.sort(Sorts.descending(paginationQueryParams.getSortField())));
        }

        pipeline.add(Aggregates.skip(paginationQueryParams.getSkip()));
        pipeline.add(Aggregates.limit(paginationQueryParams.getLimit()));

        return pipeline;
    }

    public static List<Bson> listWithPagination(PaginationQueryParams paginationQueryParams, String field) {
            List<Bson> pipeline = new ArrayList<>();

            if (paginationQueryParams.getSort() == 1) {
                pipeline.add(Aggregates.sort(Sorts.ascending(field)));
            } else {
                pipeline.add(Aggregates.sort(Sorts.descending(field)));
            }

            pipeline.add(Aggregates.skip(paginationQueryParams.getSkip()));
            pipeline.add(Aggregates.limit(paginationQueryParams.getLimit()));

            return pipeline;
        }



    public static <T> Uni<InsertResult> insertOne(ReactiveMongoCollection<T> collection, T entity) {
        return collection.insertOne(entity)
                .onItem()
                .transformToUni(item->{
                    return Uni.createFrom().item(InsertResult.fromId(item.getInsertedId().asString().getValue()));
                });
    }
    public static <T> Uni<InsertResult> insertOne(ReactiveMongoCollection<T> collection, T entity, ClientSession clientSession) {
        return collection.insertOne(clientSession, entity)
                .onItem()
                .transformToUni(item->{
                    return Uni.createFrom().item(InsertResult.fromId(item.getInsertedId().asString().getValue()));
                });
    }

    public static <T> Uni<UpdateResult> updateOne(ReactiveMongoCollection<T> collection, String id, Bson update) {

        Bson filters = Filters.eq("_id",id);
        return collection.updateOne(filters, update)
                .onItem()
                .transform(mongoResult->UpdateResult.fromCounts(
                        mongoResult.getMatchedCount(),
                        mongoResult.getModifiedCount()
                ));
    }
    public static <T> Uni<UpdateResult> updateOne(ReactiveMongoCollection<T> collection, String id, Bson update, ClientSession clientSession) {

        Bson filters = Filters.eq("_id",id);
        return collection.updateOne(clientSession, filters, update)
                .onItem()
                .transform(mongoResult->UpdateResult.fromCounts(
                        mongoResult.getMatchedCount(),
                        mongoResult.getModifiedCount()
                ));
    }
    public static <T> Uni<T> findOneByFilter(ReactiveMongoCollection<T> collection, Bson filter) {
        return collection.find(filter)
                .collect().first()
                .onItem()
                .ifNull()
                .failWith(new DocumentNotFound("Document not found",404));
    }
    public static <T> Uni<T> findOneByFilter(ReactiveMongoCollection<T> collection, Bson filter, ClientSession clientSession) {
        return collection.find(clientSession, filter)
                .collect().first()
                .onItem()
                .ifNull()
                .failWith(new DocumentNotFound("Document not found",404));
    }

    public static <T> Uni<UpdateResult> updateOne(ReactiveMongoCollection<T> collection, Bson filter, Bson update) {
        return collection.updateOne(filter, update)
                .onItem()
                .transform(mongoResult -> UpdateResult.fromCounts(
                        mongoResult.getMatchedCount(),
                        mongoResult.getModifiedCount()
                ));
    }
    public static <T> Uni<UpdateResult> updateOne(ReactiveMongoCollection<T> collection, Bson filter, Bson update, ClientSession clientSession) {
        return collection.updateOne(clientSession,filter, update)
                .onItem()
                .transform(mongoResult -> UpdateResult.fromCounts(
                        mongoResult.getMatchedCount(),
                        mongoResult.getModifiedCount()
                ));
    }

    public static <T> Uni<List<T>> findManyByFilter(ReactiveMongoCollection<T> collection, Bson filter) {
        return collection.find(filter).collect().asList();
    }


    public static <T> Uni<DeleteResult> deleteOne(ReactiveMongoCollection<T> collection, String id) {
        Bson filter = Filters.eq("_id", new ObjectId(id));
        return collection.deleteOne(filter)
                .onItem()
                .transform(mongoResult -> {
                    return DeleteResult.fromCount((int) mongoResult.getDeletedCount());
                });
    }

    public static <T> Uni<Long> count(ReactiveMongoCollection<T> collection, Bson filter) {
        return collection.countDocuments(filter);
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
    public static <T> Uni<List<T>> aggregate(ReactiveMongoCollection<?> collection, List<Bson> pipeline,PaginationQueryParams paginationQueryParams, Class<T> outputClass) {
        pipeline.addAll(MongoUtil.listWithPagination(paginationQueryParams));
        return collection
                .withDocumentClass(outputClass)
                .aggregate(pipeline, outputClass)
                .collect()
                .asList();
    }
}
