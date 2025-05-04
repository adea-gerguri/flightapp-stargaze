package shared.exceptions;

public class DocumentNotFound extends Exception {
  private final int statusCode;

  public DocumentNotFound(String message){
    super(message);
    this.statusCode = 400;
  }

  public DocumentNotFound(String message, int statusCode) {
    super(message);
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
