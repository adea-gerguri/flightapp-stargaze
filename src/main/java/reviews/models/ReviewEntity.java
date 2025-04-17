package reviews.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewEntity {
    private String id;
    private String reservationId;
    private String message;
    private int rating;
    private String reviewDate;
}
