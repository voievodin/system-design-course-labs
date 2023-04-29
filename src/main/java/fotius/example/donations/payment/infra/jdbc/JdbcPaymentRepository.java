package fotius.example.donations.payment.infra.jdbc;

import fotius.example.donations.payment.domain.repositories.PaymentRepository;
import fotius.example.donations.payment.domain.model.Currency;
import fotius.example.donations.payment.domain.model.Payment;
import fotius.example.donations.payment.domain.model.PaymentMethodTypes;
import fotius.example.donations.payment.domain.model.PaymentState;
import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class JdbcPaymentRepository implements PaymentRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Optional<Payment> findById(long id) {
        List<Payment> payments = jdbcTemplate.query(
            """
            SELECT
                id,
                amount,
                method,
                currency,
                user_id,
                state,
                created_at
            FROM payment
            WHERE id = :id
            """,
            new MapSqlParameterSource("id", id),
            (rs, rowNum) ->
                Payment.builder()
                    .id(rs.getLong("id"))
                    .amount(rs.getBigDecimal("amount"))
                    .method(PaymentMethodTypes.valueOf(rs.getString("method")))
                    .currency(Currency.valueOf(rs.getString("currency")))
                    .userId(rs.getLong("user_id"))
                    .state(PaymentState.valueOf(rs.getString("state")))
                    .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                    .build()
        );
        if (payments.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(payments.get(0));
        }
    }

    @Override
    public List<Payment> findByUserIdAndBetweenDates(Long userId, LocalDateTime from, LocalDateTime to) {
        return jdbcTemplate.query(
                """
                SELECT
                    id,
                    amount,
                    method,
                    currency,
                    user_id,
                    state,
                    created_at
                FROM payment
                WHERE user_id = :user_id
                AND created_at BETWEEN :from AND :to
                """,
                new MapSqlParameterSource()
                        .addValue("user_id", userId)
                        .addValue("from", Timestamp.valueOf(from))
                        .addValue("to", Timestamp.valueOf(to)),
                (rs, rowNum) ->
                        Payment.builder()
                                .id(rs.getLong("id"))
                                .amount(rs.getBigDecimal("amount"))
                                .method(PaymentMethodTypes.valueOf(rs.getString("method")))
                                .currency(Currency.valueOf(rs.getString("currency")))
                                .userId(rs.getLong("user_id"))
                                .state(PaymentState.valueOf(rs.getString("state")))
                                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                                .build()
        );
    }

    @Override
    public void insert(Payment payment) {
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
            """
            INSERT INTO payment (
                amount,
                method,
                currency,
                user_id,
                state,
                created_at
            ) VALUES (
                :amount,
                :method,
                :currency,
                :user_id,
                :state,
                :created_at
            )
            """,
            new MapSqlParameterSource()
                .addValue("amount", payment.getAmount())
                .addValue("method", payment.getMethod().name())
                .addValue("currency", payment.getCurrency().name())
                .addValue("user_id", payment.getUserId())
                .addValue("state", payment.getState().name())
                .addValue("created_at", Timestamp.valueOf(payment.getCreatedAt())),
            keyHolder
        );
        payment.setId(keyHolder.getKey().longValue());
    }

    @Override
    public void update(Payment payment) {
        jdbcTemplate.update(
            """
            UPDATE payment
            SET
                amount = :amount,
                method = :method,
                currency = :currency,
                user_id = :user_id,
                state = :state
            WHERE id = :id
            """,
            new MapSqlParameterSource()
                .addValue("id", payment.getId())
                .addValue("amount", payment.getAmount())
                .addValue("method", payment.getMethod().name())
                .addValue("currency", payment.getCurrency().name())
                .addValue("user_id", payment.getUserId())
                .addValue("state", payment.getState().name())
        );
    }
}
