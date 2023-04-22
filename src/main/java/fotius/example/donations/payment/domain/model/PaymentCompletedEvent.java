package fotius.example.donations.payment.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PaymentCompletedEvent {
    Payment payment;
}
