package fotius.example.donations.monitoring.domain.exceptions;

public class TrackerSystemException extends RuntimeException{
    public TrackerSystemException(String message) {
        super(message);
    }

    public TrackerSystemException(String message, Throwable cause) {
        super(message, cause);
    }
}