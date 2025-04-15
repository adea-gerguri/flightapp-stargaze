package reviews.exceptions;

public class RatingNotValidException extends RuntimeException {
    public RatingNotValidException() {
        super("Rating needs to be between 0 and 5!");
    }
}
