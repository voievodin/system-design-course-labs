package fotius.example.donations.monitoring.domain;

import fotius.example.donations.monitoring.domain.exceptions.NoPaymentsWithTrackerParams;
import fotius.example.donations.monitoring.domain.exceptions.TrackerNotFoundException;
import fotius.example.donations.monitoring.domain.model.AmountLimitType;
import fotius.example.donations.monitoring.domain.model.PaymentsSuccessRate;
import fotius.example.donations.monitoring.domain.model.Tracker;
import fotius.example.donations.payment.domain.model.Currency;
import fotius.example.donations.payment.domain.model.Payment;
import fotius.example.donations.payment.domain.model.PaymentMethod;
import fotius.example.donations.payment.domain.model.PaymentState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class TrackerService {
    private final TrackerRepository repository;

    @Transactional
    public Tracker create(
            int trackerId,
            AmountLimitType amountLimitType,
            BigDecimal amountLimit,
            Currency currency,
            PaymentMethod method
    ) {
        final Tracker tracker = Tracker.builder()
                .trackerId(trackerId)
                .currency(currency)
                .method(method)
                .amountLimit(amountLimit)
                .amountLimitType(amountLimitType)
                .build();
        repository.insert(tracker);
        return tracker;
    }

    public Tracker getById(int trackerId) {
        return repository.findById(trackerId).orElseThrow(() -> new TrackerNotFoundException(trackerId));
    }

    public PaymentsSuccessRate getSuccessRateById(int trackerId) {
        Tracker tracker = repository.findById(trackerId).orElseThrow(() -> new TrackerNotFoundException(trackerId));
        Payment[] payments = repository.findPaymentsByTrackerParams(tracker.getMethod(),
                tracker.getCurrency(),
                tracker.getAmountLimit(),
                tracker.getAmountLimitType()).orElseThrow(() -> new NoPaymentsWithTrackerParams(trackerId));

        int successPayments = 0, failedPayments = 0;
        for (int i = 0; i < payments.length; i++) {
            if (payments[i].getState() == PaymentState.COMPLETED) successPayments++;
            else if (payments[i].getState() == PaymentState.CANCELED) failedPayments++;
        }

        return PaymentsSuccessRate.builder().successfulPayments(successPayments).failPayments(failedPayments).build();
    }
}
