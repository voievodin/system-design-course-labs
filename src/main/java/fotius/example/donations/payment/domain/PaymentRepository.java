package fotius.example.donations.payment.domain;

import fotius.example.donations.monitoring.domain.model.AmountLimitType;
import fotius.example.donations.payment.domain.model.Currency;
import fotius.example.donations.payment.domain.model.Payment;
import fotius.example.donations.payment.domain.model.PaymentMethod;

import java.math.BigDecimal;
import java.util.Optional;

public interface PaymentRepository {
    Optional<Payment> findById(long id);

    void insert(Payment payment);

    void update(Payment payment);

    Optional<Payment[]> findByMethodByCurrencyWithHigherAmount(PaymentMethod method,
                                                              Currency currency,
                                                              BigDecimal amount);

    Optional<Payment[]> findByMethodByCurrencyWithLowerAmount(PaymentMethod method,
                                                             Currency currency,
                                                             BigDecimal amount);
}
