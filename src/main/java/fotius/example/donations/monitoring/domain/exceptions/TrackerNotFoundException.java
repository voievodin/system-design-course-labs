package fotius.example.donations.monitoring.domain.exceptions;

public class TrackerNotFoundException extends TrackerSystemException {
    public TrackerNotFoundException(int trackerId) {
        super("Tracker with id '%d' wasn't found".formatted(trackerId));
    }
}