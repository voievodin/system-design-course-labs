package fotius.example.donations.monitoring.domain;

import fotius.example.donations.monitoring.domain.model.AmountLimitType;
import fotius.example.donations.monitoring.domain.model.Tracker;
import fotius.example.donations.payment.domain.model.Currency;
import fotius.example.donations.payment.domain.model.Payment;
import fotius.example.donations.payment.domain.model.PaymentMethod;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Optional;

public interface TrackerRepository {
    Optional<Tracker> findById(long id);
    Optional<Tracker[]> findAll();
    void insert(Tracker tracker);
}
