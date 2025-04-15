package reviews.repository;

import airport.models.AirportEntity;
import io.quarkus.mongodb.FindOptions;
import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.bson.BsonDocument;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import reviews.exceptions.RatingNotValidException;
import reviews.models.ReviewEntity;
import reviews.models.dto.CreateReviewDto;
import reviews.models.dto.ReviewDto;
import shared.mongoUtils.DeleteResult;
import shared.mongoUtils.MongoUtil;
import shared.mongoUtils.InsertResult;
import ticket.models.TicketEntity;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Aggregates.*;
import com.mongodb.client.model.Sorts.*;
import com.mongodb.client.model.Projections.*;
import org.bson.conversions.Bson;


import java.util.List;
import java.util.stream.Stream;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;
import static java.util.stream.StreamSupport.stream;


@ApplicationScoped
public class ReviewRepository {
    @Inject
    MongoUtil mongoUtil;


    public Uni<List<ReviewEntity>> listReviews() {
        return getCollection().find(new FindOptions()).collect().asList();
    }

    public Uni<InsertResult> add(ReviewEntity review){
        return MongoUtil.insertOne(getCollection(), review);
    }

    public Uni<List<ReviewDto>> highestRated(int skip, int limit) {
        List<Bson> pipeline = List.of(
                sort(descending("rating")),
                skip(skip),
                limit(limit),
                project(fields(
                        computed("id", "$_id"),
                        include("reservationId", "rating", "reviewDate", "message")
                ))
        );
        return MongoUtil.aggregate(getCollection(), pipeline, ReviewDto.class);
    }

    public Uni<List<ReviewDto>> lowestRated(int skip, int limit) {
        List<Bson> pipeline = List.of(
                sort(ascending("rating")),
                skip(skip),
                limit(limit),
                project(fields(
                        computed("id", "$_id"),
                        include("reservationId", "rating", "reviewDate", "message")
                ))
        );
        return MongoUtil.aggregate(getCollection(), pipeline, ReviewDto.class);
    }


    private ReactiveMongoCollection<ReviewEntity> getCollection() {
        return mongoUtil.getCollection("reviews", ReviewEntity.class);
    }








}
