package fotius.example.donations.risks.domain;

import fotius.example.donations.payment.domain.PaymentMethodService;
import fotius.example.donations.payment.domain.PaymentService;
import fotius.example.donations.payment.domain.model.Payment;
import fotius.example.donations.payment.domain.model.PaymentState;
import fotius.example.donations.payment.domain.observers.PaymentCreatedObserver;
import fotius.example.donations.payment.domain.observers.PaymentCreatedObserversManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class PaymentCreateRiskOnRecurringTransactionsObserver implements PaymentCreatedObserver {

    private static final int PAYMENT_COUNT_PER_METHOD_PER_DAY = 5;

    private PaymentCreatedObserversManager paymentCreatedObserversManager;
    private PaymentService paymentService;
    private PaymentMethodService paymentMethodService;

    public PaymentCreateRiskOnRecurringTransactionsObserver(){
        paymentCreatedObserversManager.addPaymentCreatedObserver(this);
    }

    @Override
    public void onPaymentCreated(Long paymentId, Long userId) {
        Payment current_payment = paymentService.getById(paymentId);
        Payment[] payments = (Payment[]) paymentService.getPreviousPayments(userId, LocalDateTime.now().minusHours(24), LocalDateTime.now()).stream()
            .filter(payment -> payment.getMethod().equals(current_payment.getMethod()))
            .filter(payment -> payment.getState().equals(PaymentState.COMPLETED))
            .toArray();
        if (payments.length > PAYMENT_COUNT_PER_METHOD_PER_DAY) {
            paymentMethodService.blockPaymentMethod(current_payment.getMethod(), userId);
        }
    }
}
