package fotius.example.donations.payment.domain.observers;

import java.util.List;

public interface PaymentCreatedObserver {
    void onPaymentCreated(Long paymentId, Long userId);
}