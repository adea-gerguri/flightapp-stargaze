package reservation.exception;

public class ReservationException extends RuntimeException {
    private final int statusCode;

    public ReservationException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
