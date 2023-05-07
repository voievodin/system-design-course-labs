
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
