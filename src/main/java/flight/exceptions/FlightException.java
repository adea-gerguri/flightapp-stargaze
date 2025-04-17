package flight.exceptions;

public class FlightException extends RuntimeException {
    private final int statusCode;

    public FlightException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
