package fotius.example.donations.payment.domain.observer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class PaymentCreatedObserversNotifier {
    private final ArrayList<PaymentCreatedObserver> paymentCreatedObservers = new ArrayList<>();
    public void addPaymentCreatedObserver(PaymentCreatedObserver observer) {
        paymentCreatedObservers.add(observer);
    }
    public void removePaymentCreatedObserver(PaymentCreatedObserver observer) {
        paymentCreatedObservers.remove(observer);
    }
    public void notify(Long paymentId, Long userId) {
        for (PaymentCreatedObserver observer : paymentCreatedObservers) {
            observer.onPaymentCreated(paymentId, userId);
        }
    }

}
