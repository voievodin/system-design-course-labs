package fotius.example.donations.monitoring.domain;

import fotius.example.donations.monitoring.domain.exceptions.TrackerNotFoundException;
import fotius.example.donations.monitoring.domain.model.AmountLimitType;
import fotius.example.donations.monitoring.domain.model.PaymentsSuccessRate;
import fotius.example.donations.monitoring.domain.model.Tracker;
import fotius.example.donations.payment.domain.PaymentRepository;
import fotius.example.donations.payment.domain.model.Currency;
import fotius.example.donations.payment.domain.model.Payment;
import fotius.example.donations.payment.domain.model.PaymentMethod;
import fotius.example.donations.payment.domain.model.PaymentState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class TrackerService {
    private final TrackerRepository trackerRepository;
    private final PaymentRepository paymentRepository;

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
        trackerRepository.insert(tracker);
        return tracker;
    }

    public Tracker[] getAll() {
        return trackerRepository.findAll().orElse(new Tracker[0]);
    }

    public PaymentsSuccessRate getSuccessRateById(int trackerId) {
        Tracker tracker = trackerRepository.findById(trackerId).orElse(null);
        if (tracker == null) return PaymentsSuccessRate.builder().successfulPayments(0).failPayments(0).build();

        Payment[] payments = (tracker.getAmountLimitType() == AmountLimitType.HIGHER) ?
                paymentRepository.findByMethodByCurrencyWithHigherAmount(tracker.getMethod(),
                        tracker.getCurrency(),
                        tracker.getAmountLimit()).orElse(null)
                :
                paymentRepository.findByMethodByCurrencyWithLowerAmount(tracker.getMethod(),
                        tracker.getCurrency(),
                        tracker.getAmountLimit()).orElse(null);
        if (payments == null) return PaymentsSuccessRate.builder().successfulPayments(0).failPayments(0).build();

        int successPayments = 0, failedPayments = 0;
        for (int i = 0; i < payments.length; i++) {
            if (payments[i].getState() == PaymentState.COMPLETED) successPayments++;
            else if (payments[i].getState() == PaymentState.CANCELED) failedPayments++;
        }

        return PaymentsSuccessRate.builder().successfulPayments(successPayments).failPayments(failedPayments).build();
    }
}
