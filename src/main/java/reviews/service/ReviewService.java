package reviews.service;

import airline.mappers.AirlineMapper;
import airline.models.AirlineEntity;
import airline.models.dto.CreateAirlineDTO;
import airline.repository.AirlineRepository;
import io.quarkus.mongodb.FindOptions;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.BadRequestException;
import reviews.mapper.ReviewMapper;
import reviews.models.ReviewEntity;
import reviews.models.dto.CreateReviewDto;
import reviews.models.dto.ReviewDto;
import reviews.repository.ReviewRepository;
import shared.mongoUtils.DeleteResult;
import shared.mongoUtils.InsertResult;

import java.util.List;

@ApplicationScoped
public class ReviewService {
    @Inject
    ReviewRepository reviewRepository;

    public Uni<List<ReviewEntity>> listReviews() {
        return reviewRepository.listReviews();
    }

    public Uni<InsertResult> addReview(@Valid CreateReviewDto reviewDto) {
        return reviewRepository.add(ReviewMapper.toReviewEntity(reviewDto));
    }

    public Uni<List<ReviewDto>> listHighestRated(int skip, int limit){
        return reviewRepository.highestRated(skip,limit);
    }

    public Uni<List<ReviewDto>> listLowestRated(int skip, int limit){
        return reviewRepository.lowestRated(skip,limit);
    }

}
