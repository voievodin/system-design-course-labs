package fotius.example.donations.account.domain.model;

import fotius.example.donations.common.model.Currency;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class ExchangeRate {
    Currency from;
    Currency to;
    BigDecimal rate;
}
