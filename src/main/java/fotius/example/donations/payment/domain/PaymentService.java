package fotius.example.donations.payment.domain;

import fotius.example.donations.account.domain.AccountService;
import fotius.example.donations.payment.domain.model.Currency;
import fotius.example.donations.payment.domain.model.Payment;
import fotius.example.donations.payment.domain.model.PaymentMethod;
import fotius.example.donations.payment.domain.model.PaymentState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;
    private final AccountService accountService;

    @Transactional
    public Payment create(
        BigDecimal amount,
        Currency currency,
        PaymentMethod method,
        Long userId
    ) {
        final Payment payment = Payment.builder()
            .amount(amount)
            .currency(currency)
            .method(method)
            .userId(userId)
            .state(PaymentState.NEW)
            .createdAt(LocalDateTime.now())
            .build();
        repository.insert(payment);
        return payment;
    }

    @Transactional
    public Payment changeState(Long paymentId, PaymentState toState) {
        final Payment payment = repository.findById(paymentId).orElseThrow(() -> new PaymentNotFoundException(paymentId));
        if (!payment.getState().canChangeTo(toState)) {
            throw new PaymentSystemException("State transition '%s' -> '%s' isn't supported".formatted(payment.getState(), toState));
        }
        payment.setState(toState);
        repository.update(payment);

        /* Визов підсистеми підрахунку прибутку*/
        if (toState == PaymentState.COMPLETED)
            accountService.savePaymentToAccount(payment);

        return payment;
    }

    public Payment getById(Long paymentId) {
        return repository.findById(paymentId).orElseThrow(() -> new PaymentNotFoundException(paymentId));
    }
}
