package fotius.example.donations.monitoring.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PaymentsSuccessRate {
    private int successfulPayments;
    private int failPayments;
}
