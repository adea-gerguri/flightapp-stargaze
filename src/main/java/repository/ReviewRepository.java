package repository;

import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import model.dto.ReviewDto;
import model.mapper.MapperService;
import model.mapper.ReviewMapper;
import model.view.Review;
import org.bson.Document;

import java.sql.Date;
import java.util.List;

@ApplicationScoped
public class ReviewRepository {

    @Inject
    ReactiveMongoClient mongoClient;

    @Inject
    MapperService mapper;

    public Uni<List<ReviewDto>> listReviews(){
        return getCollection().find()
                .map(document->{
                    Review review = new Review();
                    review.setMessage(document.getString("message"));
                    review.setRating(document.getInteger(("rating")));
                    review.setReviewDate((Date) document.getDate("reviewDate")); //check and fix parsing here
                    return mapper.map(review, ReviewDto.class);
                }).collect().asList();
    }
    public Uni<Void> addReview(Review review){
        Document document = new Document()
                .append("message", review.getMessage())
                .append("rating", review.getRating())
                .append("reviewDate", review.getReviewDate()); // check and fix this
        return getCollection().insertOne(document)
                .onItem().ignore().andContinueWithNull();
    }
    private ReactiveMongoCollection<Document> getCollection(){
        return mongoClient.getDatabase("stargaze").getCollection("reviews");
    }
}
