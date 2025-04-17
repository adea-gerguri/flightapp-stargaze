package reviews.exceptions;

public class ReviewException extends RuntimeException {
    private final int statusCode;

    public ReviewException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
