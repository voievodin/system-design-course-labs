/**
 * AccountClosedException.java
 *
 * @author Kostyantin Solod
 * Date of creation: 26-Apr-2023 09:15
 */

package fotius.example.donations.account.domain;

import fotius.example.donations.payment.domain.model.Currency;

public class AccountClosedException extends RuntimeException {
    public AccountClosedException(Currency currency) {
        super("Attempt to add money to closed account:" + currency.toString());
    }

    public AccountClosedException(String message, Throwable cause) {
        super(message, cause);
    }
}
