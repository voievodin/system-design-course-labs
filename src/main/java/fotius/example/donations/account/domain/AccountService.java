/**
 * AccountService.java
 *
 * @author Kostyantin Solod
 * Date of creation: 25-Apr-2023 21:01
 */

package fotius.example.donations.account.domain;

import fotius.example.donations.account.domain.model.Account;
import fotius.example.donations.payment.domain.model.Currency;
import fotius.example.donations.payment.domain.model.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository repository;
    private static final int SYSTEM_INTEREST = 5; // 5% відсоток, який бере система

    @Transactional
    public Account open(Currency currency) {
        final Account account = repository.findByCurrency(currency).orElse(null);
        //orElseThrow(() -> new PaymentNotFoundException(paymentId));

        account.setOpened(true);
        repository.update(account);
        return account;
    }

    @Transactional
    public Account close(Currency currency) {
        final Account account = repository.findByCurrency(currency).orElseThrow(() -> new AccountNotFoundException(currency));
        account.setOpened(false);
        repository.update(account);
        return account;
    }

    @Transactional
    public List<Account> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Account findByCurrency(Currency currency) {
        return repository.findByCurrency(currency).orElse(null);
    }

    @Transactional
    public void savePaymentToAccount(Payment payment) {
        Currency currency = payment.getCurrency();
        BigDecimal sum = payment.getAmount();
        Account account = repository.findByCurrency(currency).orElse(null);
        // якщо рахунок закритий, то нічого не додаємо
        if (account != null && !account.isOpened()){
            //throw new PaymentNotFoundException(1L);
            throw new AccountClosedException(account.getCurrency());
        }
        BigDecimal newSum =
                sum.subtract(
                        sum.divide(BigDecimal.valueOf(100)).multiply(BigDecimal.valueOf(SYSTEM_INTEREST))
                );
        account.setBalance(account.getBalance().add(newSum));
        repository.update(account);
    }

}
