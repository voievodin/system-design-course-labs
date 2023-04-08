package fotius.example.donations.payment.domain.exeptions;

public class PaymentNotFoundException extends PaymentSystemException {
    public PaymentNotFoundException(Long paymentId) {
        super("Payment with id '%d' wasn't found".formatted(paymentId));
    }
}
