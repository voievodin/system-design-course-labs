package fotius.example.donations.payment.domain.services;

import fotius.example.donations.payment.domain.exeptions.PaymentNotFoundException;
import fotius.example.donations.payment.domain.exeptions.PaymentSystemException;
import fotius.example.donations.payment.domain.model.Currency;
import fotius.example.donations.payment.domain.model.Payment;
import fotius.example.donations.payment.domain.model.PaymentMethodTypes;
import fotius.example.donations.payment.domain.model.PaymentState;
import fotius.example.donations.payment.domain.observer.PaymentCreatedObserversNotifier;
import fotius.example.donations.payment.domain.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository repository;
    private final PaymentCreatedObserversNotifier paymentCreatedObserversNotifier;

    @Transactional
    public Payment create(
        BigDecimal amount,
        Currency currency,
        PaymentMethodTypes method,
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
        paymentCreatedObserversNotifier.notify(payment.getId(), userId);
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
        return payment;
    }

    public Payment getById(Long paymentId) {
        return repository.findById(paymentId).orElseThrow(() -> new PaymentNotFoundException(paymentId));
    }

    public List<Payment> getPreviousPayments(Long userId, LocalDateTime from, LocalDateTime to) {
        return repository.findByUserIdAndBetweenDates(userId, from, to);
    }
}
