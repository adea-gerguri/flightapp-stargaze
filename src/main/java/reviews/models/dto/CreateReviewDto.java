package reviews.models.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull private String reservationId;

    @NoForbiddenWords(message = "be respectful to the community")
    private String message;
    @NotNull private String reviewDate;

    @Min(value = 0, message = "rating must be between 0 and 5")
    @Max(value = 5, message = "rating must be between 0 and 5")
    @NotNull private int rating;

}
