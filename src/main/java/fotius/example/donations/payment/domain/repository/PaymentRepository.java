package fotius.example.donations.payment.domain.repository;

import fotius.example.donations.payment.domain.model.Payment;

import java.util.Optional;

public interface PaymentRepository {
    Optional<Payment> findById(long id);

    void insert(Payment payment);

    void update(Payment payment);
}
