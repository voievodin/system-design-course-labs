/**
 * Account.java
 *
 * @author Kostyantin Solod
 * Date of creation: 25-Apr-2023 20:26
 */

package fotius.example.donations.account.domain.model;

import fotius.example.donations.payment.domain.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class Account {
   private Long id;
   private BigDecimal balance;
   private Currency currency;
   private boolean isOpened;

}
