package fotius.example.donations.monitoring.domain.model;


import fotius.example.donations.payment.domain.model.Currency;
import fotius.example.donations.payment.domain.model.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class Tracker {
    private Long id;
    private int trackerId;
    private PaymentMethod method;
    private Currency currency;
    private BigDecimal amountLimit;
    private AmountLimitType amountLimitType;
}