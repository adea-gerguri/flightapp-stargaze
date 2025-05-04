package user.exceptions;

public class UserException extends RuntimeException {
    private final int statusCode;

    public UserException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
