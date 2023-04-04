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
public class PaymentCreateRiskOnRecurringTransactionsObserver implements PaymentCreatedObserver {

    private static final int PAYMENT_CRITICAL_COUNT_PER_METHOD = 5;
    private static final int RISK_CHECKING_CRITICAL_COUNT_DAYS = 1;
    private static final int RISK_BLOCKING_DAYS = 1;

    private final PaymentService paymentService;
    private final PaymentMethodService paymentMethodService;

    public PaymentCreateRiskOnRecurringTransactionsObserver(PaymentCreatedObserversManager paymentCreatedObserversManager,
                                                            PaymentService paymentService,
                                                            PaymentMethodService paymentMethodService) {
        this.paymentService = paymentService;
        this.paymentMethodService = paymentMethodService;
        paymentCreatedObserversManager.addPaymentCreatedObserver(this);
    }

    @Override
    public void onPaymentCreated(Long paymentId, Long userId) {
        Payment current_payment = paymentService.getById(paymentId);
        Payment[] payments = (Payment[]) paymentService.getPreviousPayments(userId, LocalDateTime.now().minusDays(RISK_CHECKING_CRITICAL_COUNT_DAYS), LocalDateTime.now()).stream()
            .filter(payment -> payment.getMethod().equals(current_payment.getMethod()))
            .filter(payment -> payment.getState().equals(PaymentState.COMPLETED))
            .toArray();
        if (payments.length > PAYMENT_CRITICAL_COUNT_PER_METHOD) {
            paymentMethodService.blockPaymentMethod(current_payment.getMethod(), userId, LocalDateTime.now().plusDays(RISK_BLOCKING_DAYS));
        }
    }
}
