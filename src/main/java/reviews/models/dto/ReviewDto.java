package reviews.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import reviews.models.ReviewEntity;
import org.bson.types.ObjectId;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    @NotBlank
    private ObjectId userId;

    @NotBlank
    private String message;

    @NotBlank
    private int rating;

    @NotBlank
    private String reviewDate;

}
