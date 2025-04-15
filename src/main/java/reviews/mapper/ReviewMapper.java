package reviews.mapper;

import jakarta.enterprise.context.RequestScoped;
import reviews.models.dto.CreateReviewDto;
import reviews.models.dto.ReviewDto;
import reviews.models.ReviewEntity;

@RequestScoped
public class ReviewMapper {
    public static ReviewEntity toReviewEntity(CreateReviewDto reviewDto){
        if(reviewDto!=null){
            ReviewEntity reviewEntity = new ReviewEntity();

            reviewEntity.setReviewDate(reviewDto.getReviewDate());
            reviewEntity.setRating(reviewDto.getRating());
            reviewEntity.setUserId(reviewDto.getUserId());
            reviewEntity.setMessage(reviewDto.getMessage());
            return reviewEntity;
        }
        return null;
    }
    public static ReviewDto toReviewDto(ReviewEntity reviewEntity){
        if(reviewEntity !=null){
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setReviewDate(reviewEntity.getReviewDate());
            reviewDto.setRating(reviewEntity.getRating());
            reviewDto.setMessage(reviewEntity.getMessage());
            return reviewDto;
        }
        return null;
    }
}
