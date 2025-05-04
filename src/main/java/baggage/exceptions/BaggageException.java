package baggage.exceptions;

public class BaggageException extends RuntimeException {
  private final int statusCode;

  public BaggageException(String message, int statusCode) {
    super(message);
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
