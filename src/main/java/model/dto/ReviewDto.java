package model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.view.Review;
import org.bson.types.ObjectId;

import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    @NotBlank
    private ObjectId id;

    @NotBlank
    private ObjectId userId;

    @NotBlank
    private String message;

    @NotBlank
    private int rating;

    @NotBlank
    private Date reviewDate;

    public ReviewDto(Review review) {
        this.setId(review.getId());
        this.setUserId(review.getUserId());
        this.setMessage(review.getMessage());
        this.setRating(review.getRating());
        this.setReviewDate(review.getReviewDate());
    }
}
