package reviews.service;

import airline.mappers.AirlineMapper;
import airline.models.AirlineEntity;
import airline.models.dto.CreateAirlineDTO;
import airline.repository.AirlineRepository;
import io.quarkus.mongodb.FindOptions;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.ws.rs.BadRequestException;
import reviews.exceptions.ReviewException;
import reviews.mapper.ReviewMapper;
import reviews.models.ReviewEntity;
import reviews.models.dto.CreateReviewDto;
import reviews.models.dto.HighestRatedReviewDto;
import reviews.models.dto.LowestRatedReviewDto;
import reviews.models.dto.ReviewDto;
import reviews.repository.ReviewRepository;
import shared.GlobalHibernateValidator;
import shared.mongoUtils.DeleteResult;
import shared.mongoUtils.InsertResult;

import java.util.List;

@ApplicationScoped
public class ReviewService {
    @Inject
    ReviewRepository reviewRepository;

    @Inject
    GlobalHibernateValidator validator;

    public Uni<List<ReviewEntity>> listReviews() {
        return reviewRepository.listReviews();
    }

    public Uni<InsertResult> addReview(@Valid CreateReviewDto reviewDto) {
        return validator.validate(reviewDto)
                .onFailure(ConstraintViolationException.class)
                .transform(e->new ReviewException(e.getMessage(),400))
                .flatMap(validatedDto->{
                    return reviewRepository.add(ReviewMapper.toReviewEntity(validatedDto));
                });
    }

    public Uni<List<HighestRatedReviewDto>> listHighestRated(int skip, int limit){
        return reviewRepository.highestRated(skip,limit)
                .onFailure()
                .transform(e-> new ReviewException(e.getMessage(),404));
    }

    public Uni<List<LowestRatedReviewDto>> listLowestRated(int skip, int limit){
        return reviewRepository.lowestRated(skip,limit)
                .onFailure()
                .transform(e-> new ReviewException(e.getMessage(),404));
    }

}

