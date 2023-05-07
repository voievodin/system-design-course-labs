package fotius.example.donations.payment.domain;

public class PaymentSystemException extends RuntimeException {
    public PaymentSystemException(String message) {
        super(message);
    }

    public PaymentSystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
