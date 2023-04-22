package fotius.example.donations.account.presentation.rest;

import fotius.example.donations.account.domain.AccountService;
import fotius.example.donations.account.domain.model.Account;
import fotius.example.donations.account.domain.model.Balance;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Account account) {
        long accountId = accountService.create(account);
        return ResponseEntity
                .created(URI.create("/api/accounts/%d".formatted(accountId)))
                .build();
    }

    @GetMapping
    public List<Account> findAll() {
        return accountService.findALl();
    }

    @GetMapping("{id}")
    public Account getById(@PathVariable long id) {
        return accountService.getById(id);
    }

    @DeleteMapping("{id}")
    public void close(@PathVariable long id) {
        accountService.close(id);
    }

    @GetMapping("{id}/balance")
    public Balance getBalanceById(@PathVariable long id) {
        return accountService.getBalanceById(id);
    }
}
