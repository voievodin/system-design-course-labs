package fotius.example.donations.payment.domain.observer;

public interface PaymentCreatedObserver {
    void onPaymentCreated(Long paymentId, Long userId);
}
