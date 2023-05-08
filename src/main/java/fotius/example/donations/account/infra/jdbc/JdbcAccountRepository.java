/**
 * JdbcAccountRepository.java
 *
 * @author Kostyantin Solod
 * Date of creation: 25-Apr-2023 20:36
 */

package fotius.example.donations.account.infra.jdbc;

import fotius.example.donations.account.domain.AccountRepository;
import fotius.example.donations.account.domain.model.Account;
import fotius.example.donations.payment.domain.model.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class JdbcAccountRepository implements AccountRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Optional<Account> findById(long id) {
        List<Account> accounts = jdbcTemplate.query(
                """
                        SELECT
                            id,
                            balance,
                            currency,
                            is_opened

                        FROM account
                        WHERE id = :id
                        """,
                new MapSqlParameterSource("id", id),
                (rs, rowNum) ->
                        Account.builder()
                                .id(rs.getLong("id"))
                                .balance(rs.getBigDecimal("balance"))
                                .currency(Currency.valueOf(rs.getString("currency")))
                                .isOpened(rs.getBoolean("is_opened"))
                                .build()

        );
        if (accounts.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(accounts.get(0));
        }

    }


    @Override
    public Optional<Account> findByCurrency(Currency currency) {
        List<Account> accounts = jdbcTemplate.query(
                """
                        SELECT
                            id,
                            balance,
                            currency,
                            is_opened

                        FROM account
                        WHERE currency = :currency
                        """,
                new MapSqlParameterSource("currency", currency.toString()),
                (rs, rowNum) ->
                        Account.builder()
                                .id(rs.getLong("id"))
                                .balance(rs.getBigDecimal("balance"))
                                .currency(Currency.valueOf(rs.getString("currency")))
                                .isOpened(rs.getBoolean("is_opened"))
                                .build()

        );
        if (accounts.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(accounts.get(0));
        }

    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = jdbcTemplate.query(
                """
                        SELECT
                            id,
                            balance,
                            currency,
                            is_opened

                        FROM account
                        """,

                (rs, rowNum) ->
                        Account.builder()
                                .id(rs.getLong("id"))
                                .balance(rs.getBigDecimal("balance"))
                                .currency(Currency.valueOf(rs.getString("currency")))
                                .isOpened(rs.getBoolean("is_opened"))
                                .build()

        );
        return accounts;
    }

    @Override
    public void insert(Account account) {

    }

    @Override
    public void update(Account account) {
        jdbcTemplate.update(
                """
                        UPDATE account
                        SET
                            balance = :balance,
                            currency = :currency,
                            is_opened = :isOpened
                        WHERE id = :id
                        """,
                new MapSqlParameterSource()
                        .addValue("id", account.getId())
                        .addValue("balance", account.getBalance())
                        .addValue("currency", account.getCurrency().name())
                        .addValue("isOpened", account.isOpened())
        );

    }
}
