package fotius.example.donations.account.domain;

import fotius.example.donations.account.domain.exception.AccountNotFoundException;
import fotius.example.donations.account.domain.model.Account;
import fotius.example.donations.account.domain.model.Balance;
import fotius.example.donations.account.domain.model.BalanceChangedEvent;
import fotius.example.donations.account.domain.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static fotius.example.donations.account.domain.model.Account.Status.CLOSED;
import static fotius.example.donations.account.domain.model.Account.Status.OPEN;

@Service
public record AccountService(AccountRepository accountRepository, ExchangeService exchangeService) {

    public long create(Account account) {
        account.setStatus(OPEN);
        account.setBalance(new Balance(BigDecimal.ZERO));
        return accountRepository.save(account);
    }

    public Account getById(long id) {
        return accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
    }

    public List<Account> findALl() {
        return accountRepository.findAll();
    }

    public Balance getBalanceById(long id) {
        return getById(id).getBalance();
    }

    public void close(long id) {
        Account account = getById(id);
        account.setStatus(CLOSED);
        accountRepository.save(account);
    }

    public void updateBalance(long accountId, BalanceChangedEvent balanceChangedEvent) {
        Account account = getById(accountId);
        if (account.isClosed()) {
            return;
        }
        BigDecimal rate = exchangeService.getExchangeRate(balanceChangedEvent.getCurrency(), account.getCurrency()).getRate();
        BigDecimal newBalance = account.getBalance().value()
                .add(balanceChangedEvent.getAmount().multiply(rate))
                .setScale(2, RoundingMode.HALF_UP);
        account.setBalance(new Balance(newBalance));
        accountRepository.save(account);
    }
}
