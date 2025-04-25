package flight.exceptions;

public class FlightException extends RuntimeException {
  private final int statusCode;

  public FlightException(int statusCode, String message) {
    super(message);
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
