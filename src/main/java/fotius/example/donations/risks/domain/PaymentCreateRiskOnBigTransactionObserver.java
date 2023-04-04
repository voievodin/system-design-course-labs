package fotius.example.donations.risks.domain;

import fotius.example.donations.payment.domain.PaymentMethodService;
import fotius.example.donations.payment.domain.PaymentService;
import fotius.example.donations.payment.domain.model.Payment;
import fotius.example.donations.payment.domain.model.PaymentState;
import fotius.example.donations.payment.domain.observers.PaymentCreatedObserver;
import fotius.example.donations.payment.domain.observers.PaymentCreatedObserversManager;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class PaymentCreateRiskOnBigTransactionObserver implements PaymentCreatedObserver {

    private static final BigDecimal CRITICAL_PAYMENT_TRANSACTION_AMOUNT = new BigDecimal(1000);
    private final PaymentService paymentService;
    private final PaymentMethodService paymentMethodService;


    public PaymentCreateRiskOnBigTransactionObserver(PaymentCreatedObserversManager paymentCreatedObserversManager,
                                                     PaymentService paymentService,
                                                     PaymentMethodService paymentMethodService) {
        this.paymentService = paymentService;
        this.paymentMethodService = paymentMethodService;
        paymentCreatedObserversManager.addPaymentCreatedObserver(this);
    }

    @Override
    public void onPaymentCreated(Long paymentId, Long userId) {
        Payment current_payment = paymentService.getById(paymentId);
        if (current_payment.getAmount().compareTo(CRITICAL_PAYMENT_TRANSACTION_AMOUNT) >= 0) {
            paymentMethodService.blockAllPaymentMethods(userId);
        }
    }
}
