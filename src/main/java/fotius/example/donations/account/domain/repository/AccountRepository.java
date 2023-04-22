package fotius.example.donations.account.domain.repository;

import fotius.example.donations.account.domain.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {

    long save(Account account);

    Optional<Account> findById(long id);

    List<Account> findAll();
}
