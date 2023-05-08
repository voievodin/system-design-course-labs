/**
 * AccountNotFoundException.java
 *
 * @author Kostyantin Solod
 * Date of creation: 26-Apr-2023 10:15
 */

package fotius.example.donations.account.domain;

import fotius.example.donations.payment.domain.model.Currency;

public class AccountNotFoundException extends RuntimeException{
   public AccountNotFoundException(Long id) {
      super("Account with '" + id + "' not found in DB");
   }
   public AccountNotFoundException(Currency currency) {
      super("Account with currency '" + currency.toString() + "' not found in DB");
   }

}
