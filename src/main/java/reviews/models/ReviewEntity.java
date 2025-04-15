package reviews.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import reviews.models.dto.ReviewDto;
import org.bson.types.ObjectId;

import java.sql.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewEntity {
    private String id;
    private String userId;
    private String message;
    private int rating;
    private String reviewDate;
}
