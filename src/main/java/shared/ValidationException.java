package shared;

import lombok.Builder;


public class ValidationException extends RuntimeException {
    private final int statusCode;

    public ValidationException(String message){
        super(message);
        this.statusCode = 400;
    }

    public ValidationException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
