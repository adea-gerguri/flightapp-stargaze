package airport.exceptions;

public class AirportException extends Exception {
  private final int statusCode;

  public AirportException(String message, int statusCode) {
    super(message);
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
