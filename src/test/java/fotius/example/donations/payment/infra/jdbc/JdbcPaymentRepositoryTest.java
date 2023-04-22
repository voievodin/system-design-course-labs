package fotius.example.donations.payment.infra.jdbc;

import fotius.example.donations.common.model.Currency;
import fotius.example.donations.payment.domain.model.Payment;
import fotius.example.donations.payment.domain.model.PaymentMethod;
import fotius.example.donations.payment.domain.model.PaymentState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class JdbcPaymentRepositoryTest {

    @Autowired
    private JdbcPaymentRepository repository;

    @Test
    void insertsAndFindsPayment() {
        final Payment inserted = Payment.builder()
            .amount(new BigDecimal(100))
            .currency(Currency.EUR)
            .method(PaymentMethod.CARD)
            .userId(123L)
            .state(PaymentState.NEW)
            .createdAt(LocalDateTime.now().withNano(0))
            .build();

        repository.insert(inserted);

        assertNotNull(inserted.getId());
        assertEquals(inserted, repository.findById(inserted.getId()).get());
    }
}
