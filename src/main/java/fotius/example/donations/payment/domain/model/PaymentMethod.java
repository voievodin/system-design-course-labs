package fotius.example.donations.payment.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class PaymentMethod {
    private Long id;
    private Long userId;
    private PaymentMethodTypes type;
    private boolean isAvailable;
    private LocalDateTime availability_timestamp_from;
}
