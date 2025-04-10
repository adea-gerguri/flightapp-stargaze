package model.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.dto.ReviewDto;
import org.bson.types.ObjectId;

import java.sql.Date;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private ObjectId id;
    private ObjectId userId;
    private String message;
    private int rating;
    private Date reviewDate;

    public Review(ReviewDto reviewDto){
        this.setId(reviewDto.getId());
        this.setUserId(reviewDto.getUserId());
        this.setUserId(reviewDto.getUserId());
        this.setRating(reviewDto.getRating());
        this.setReviewDate(reviewDto.getReviewDate());
    }
}
