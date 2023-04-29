package fotius.example.donations.payment.domain.repositories;

import fotius.example.donations.payment.domain.model.Payment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository {
    Optional<Payment> findById(long id);

    List<Payment> findByUserIdAndBetweenDates(Long userId, LocalDateTime from, LocalDateTime to);

    void insert(Payment payment);

    void update(Payment payment);
}
