package ticket.exceptions;

public class TicketException extends RuntimeException {
    private final int statusCode;

    public TicketException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
