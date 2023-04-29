package fotius.example.donations.risks.domain;

import fotius.example.donations.payment.domain.services.PaymentMethodService;
import fotius.example.donations.payment.domain.services.PaymentService;
import fotius.example.donations.payment.domain.model.Payment;
import fotius.example.donations.payment.domain.observers.PaymentCreatedObserver;
import fotius.example.donations.payment.domain.observers.PaymentCreatedObserversManager;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PreventBigSumPaymentCreation implements PaymentCreatedObserver {

    private static final BigDecimal MAX_PAYMENT_AMOUNT = new BigDecimal(1000);
    private final PaymentService paymentService;
    private final PaymentMethodService paymentMethodService;


    public PreventBigSumPaymentCreation(PaymentCreatedObserversManager paymentCreatedObserversManager,
                                                          PaymentService paymentService,
                                                          PaymentMethodService paymentMethodService) {
        this.paymentService = paymentService;
        this.paymentMethodService = paymentMethodService;
        paymentCreatedObserversManager.addPaymentCreatedObserver(this);
    }

    @Override
    public void onPaymentCreated(Long paymentId, Long userId) {
        Payment current_payment = paymentService.getById(paymentId);

        if (current_payment.getAmount().compareTo(MAX_PAYMENT_AMOUNT) >= 0) {
            paymentMethodService.blockAllPaymentMethods(userId);
        }
    }
}
