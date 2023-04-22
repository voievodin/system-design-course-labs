package fotius.example.donations.account.domain.model;

import fotius.example.donations.common.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeBalanceCommand {
    private long accountId;
    private BigDecimal amount;
    private Currency currency;
}
