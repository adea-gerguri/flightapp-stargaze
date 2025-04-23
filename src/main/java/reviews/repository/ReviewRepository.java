package reviews.repository;

import io.quarkus.mongodb.FindOptions;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.conversions.Bson;
import reviews.models.ReviewEntity;
import reviews.models.dto.HighestRatedReviewDto;
import reviews.models.dto.LowestRatedReviewDto;
import shared.PaginationQueryParams;
import shared.mongoUtils.MongoUtil;
import shared.mongoUtils.InsertResult;
import java.util.List;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;


@ApplicationScoped
public class ReviewRepository {
    @Inject
    MongoUtil mongoUtil;

    public Uni<InsertResult> add(ReviewEntity review){
        return MongoUtil.insertOne(getCollection(), review);
    }

    public Uni<List<HighestRatedReviewDto>> highestRated(PaginationQueryParams paginationQueryParams) {
        List<Bson> pipeline = List.of(
                project(fields(
                        computed("id", "$_id"),
                        include("rating", "reviewDate", "message")
                ))
        );
        return MongoUtil.aggregate(getCollection(), pipeline, paginationQueryParams, HighestRatedReviewDto.class);
    }

    public Uni<List<LowestRatedReviewDto>> lowestRated(PaginationQueryParams paginationQueryParams) {
        List<Bson> pipeline = List.of(
                project(fields(
                        computed("id", "$_id"),
                        include("rating", "reviewDate", "message")
                ))
        );
        return MongoUtil.aggregate(getCollection(), pipeline, paginationQueryParams, LowestRatedReviewDto.class);
    }

    private ReactiveMongoCollection<ReviewEntity> getCollection() {
        return mongoUtil.getCollection("reviews", ReviewEntity.class);
    }








}
