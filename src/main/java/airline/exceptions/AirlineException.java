package airline.exceptions;

public class AirlineException extends RuntimeException {
  private final int statusCode;

  public AirlineException(String message, int statusCode) {
    super(message);
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return statusCode;
  }
}

