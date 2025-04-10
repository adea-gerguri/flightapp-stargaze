package model.mapper;

import jakarta.enterprise.context.RequestScoped;
import model.dto.ReviewDto;
import model.view.Review;

@RequestScoped
public class ReviewMapper {
    public Review toReview(ReviewDto reviewDto){
        if(reviewDto!=null){
            Review review = new Review();
            review.setId(reviewDto.getId());
            review.setReviewDate(reviewDto.getReviewDate());
            review.setRating(reviewDto.getRating());
            review.setUserId(reviewDto.getUserId());
            review.setMessage(reviewDto.getMessage());
            return review;
        }
        return null;
    }
    public ReviewDto toReviewDto(Review review){
        if(review!=null){
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setId(review.getId());
            reviewDto.setReviewDate(review.getReviewDate());
            reviewDto.setRating(review.getRating());
            reviewDto.setUserId(review.getUserId());
            reviewDto.setMessage(review.getMessage());
            return reviewDto;
        }
        return null;
    }
}
