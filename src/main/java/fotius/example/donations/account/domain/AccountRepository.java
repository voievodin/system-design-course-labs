package fotius.example.donations.account.domain;

import fotius.example.donations.account.domain.model.Account;
import fotius.example.donations.payment.domain.model.Currency;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    Optional<Account> findById(long id);
    Optional<Account> findByCurrency(Currency currency);
    List<Account> findAll();



    void insert(Account account);

    void update(Account account);
}
