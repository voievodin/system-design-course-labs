package fotius.example.donations.account.domain;

import fotius.example.donations.common.model.Currency;
import fotius.example.donations.account.domain.model.ExchangeRate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ExchangeService {

    private static final int PRECISION = 9;

    public ExchangeRate getExchangeRate(Currency from, Currency to) {
        return ExchangeRate.builder()
                .from(from)
                .to(to)
                .rate(calculateExchangeRate(from, to))
                .build();
    }

    private BigDecimal calculateExchangeRate(Currency from, Currency to) {
        return BigDecimal.valueOf(from.getExchangeRate()).setScale(PRECISION, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(to.getExchangeRate()), RoundingMode.HALF_UP);
    }
}
