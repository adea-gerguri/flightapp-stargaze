package reviews.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    @NotNull private String reservationId;
    private String message;
    @Range(min=1, max=5)
    @NotNull private int rating;
    @NotNull private String reviewDate;

}
