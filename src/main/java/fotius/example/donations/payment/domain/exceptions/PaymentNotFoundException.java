package fotius.example.donations.payment.domain.exceptions;

import fotius.example.donations.payment.domain.exceptions.PaymentSystemException;

public class PaymentNotFoundException extends PaymentSystemException {
    public PaymentNotFoundException(Long paymentId) {
        super("Payment with id '%d' wasn't found".formatted(paymentId));
    }
}
