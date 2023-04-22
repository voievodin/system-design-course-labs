package fotius.example.donations.account.infrastructure.memory;

import fotius.example.donations.account.domain.model.Account;
import fotius.example.donations.account.domain.repository.AccountRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryAccountRepository implements AccountRepository {

    private final AtomicLong sequence = new AtomicLong();
    private final Map<Long, Account> storage = new HashMap<>();

    @Override
    public long save(Account account) {
        Long id = account.getId();
        if (Objects.isNull(id)) {
            id = sequence.incrementAndGet();
            account.setId(id);
        }
        storage.put(id, account);

        return id;
    }

    @Override
    public Optional<Account> findById(long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Account> findAll() {
        return new ArrayList<>(storage.values());
    }
}
