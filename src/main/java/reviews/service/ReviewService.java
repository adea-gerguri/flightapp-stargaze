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
import shared.PaginationQueryParams;
import shared.mongoUtils.DeleteResult;
import shared.mongoUtils.InsertResult;

import java.util.List;

@ApplicationScoped
public class ReviewService {
    @Inject
    ReviewRepository reviewRepository;

    @Inject
    GlobalHibernateValidator validator;


    public Uni<InsertResult> addReview(CreateReviewDto reviewDto) {
        return validator.validate(reviewDto)
                .flatMap(validatedDto->{
                    return reviewRepository.add(ReviewMapper.toReviewEntity(validatedDto));
                });
    }

    public Uni<List<HighestRatedReviewDto>> listHighestRated(PaginationQueryParams paginationQueryParams){
        return reviewRepository.highestRated(paginationQueryParams);
    }

    public Uni<List<LowestRatedReviewDto>> listLowestRated(PaginationQueryParams paginationQueryParams){
        return reviewRepository.lowestRated(paginationQueryParams);
    }

}

