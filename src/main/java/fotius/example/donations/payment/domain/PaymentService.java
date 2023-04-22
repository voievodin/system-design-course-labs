package fotius.example.donations.payment.domain;

import fotius.example.donations.common.model.Currency;
import fotius.example.donations.payment.domain.model.PaymentCompletedEvent;
import fotius.example.donations.payment.domain.exception.PaymentNotFoundException;
import fotius.example.donations.payment.domain.exception.PaymentSystemException;
import fotius.example.donations.payment.domain.model.Payment;
import fotius.example.donations.payment.domain.model.PaymentMethod;
import fotius.example.donations.payment.domain.model.PaymentState;
import fotius.example.donations.payment.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;
    private final ApplicationEventPublisher publisher;

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
        if (payment.isCompleted()) {
            publisher.publishEvent(PaymentCompletedEvent.builder()
                    .payment(payment)
                    .build());
        }
        return payment;
    }

    public Payment getById(Long paymentId) {
        return repository.findById(paymentId).orElseThrow(() -> new PaymentNotFoundException(paymentId));
    }
}
