package fotius.example.donations.monitoring.domain.exceptions;

public class NoPaymentsWithTrackerParams extends TrackerSystemException {
    public NoPaymentsWithTrackerParams(int trackerId) {
        super("No payments found with this tracker params (tracker id: %d)".formatted(trackerId));
    }
}
