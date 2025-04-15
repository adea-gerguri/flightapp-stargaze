package reviews.models.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import reviews.validations.NoForbiddenWords;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateReviewDto {
    private String userId;

    @NotBlank(message = "must not be blank")
    @NoForbiddenWords(message = "be respectful to the community")
    private String message;

    @Min(value = 0, message = "rating must be between 0 and 5")
    @Max(value = 5, message = "rating must be between 0 and 5")
    private int rating;

    @NotBlank(message="must not be blank")
    private String reviewDate;
}
