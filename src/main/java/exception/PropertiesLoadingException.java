package exception;

public class PropertiesLoadingException extends RuntimeException {
    public PropertiesLoadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
