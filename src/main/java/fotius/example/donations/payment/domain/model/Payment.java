package fotius.example.donations.payment.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class Payment {
    private Long id;
    private BigDecimal amount;
    private PaymentMethod method;
    private Currency currency;
    private Long userId;
    private PaymentState state;
    private LocalDateTime createdAt;
}
