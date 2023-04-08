package fotius.example.donations.monitoring.infra.fdbc;

import fotius.example.donations.monitoring.domain.TrackerRepository;
import fotius.example.donations.monitoring.domain.model.AmountLimitType;
import fotius.example.donations.monitoring.domain.model.Tracker;
import fotius.example.donations.payment.domain.model.Currency;
import fotius.example.donations.payment.domain.model.Payment;
import fotius.example.donations.payment.domain.model.PaymentMethod;
import fotius.example.donations.payment.domain.model.PaymentState;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class JdbcTrackerRepository implements TrackerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Optional<Payment[]> findPaymentsByTrackerParams(PaymentMethod method,
                                                           Currency currency,
                                                           BigDecimal amountLimit,
                                                           AmountLimitType amountLimitType) {
        List<Payment> payments = jdbcTemplate.query(
                "SELECT " +
                    "id, amount, method, currency, user_id, state, created_at " +
                    "FROM payment " +
                    "WHERE " +
                        "method = :method AND " +
                        "currency = :currency AND " +
                        "amount "+ (amountLimitType == AmountLimitType.HIGHER ? ">" : "<") +" :amount_limit ",
                new MapSqlParameterSource()
                        .addValue("method", method)
                        .addValue("currency", currency)
                        .addValue("amount_limit", amountLimit),
                (rs, rowNum) ->
                        Payment.builder()
                                .id(rs.getLong("id"))
                                .amount(rs.getBigDecimal("amount"))
                                .method(PaymentMethod.valueOf(rs.getString("method")))
                                .currency(Currency.valueOf(rs.getString("currency")))
                                .userId(rs.getLong("user_id"))
                                .state(PaymentState.valueOf(rs.getString("state")))
                                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                                .build()
        );
        if (payments.isEmpty()) {
            return Optional.empty();
        } else {
            Payment[] result = new Payment[payments.size()];
            payments.toArray(result);
            return Optional.of(result);
        }
    }

    @Override
    public Optional<Tracker> findById(long id) {
        List<Tracker> trackers = jdbcTemplate.query(
                """
                SELECT
                    id,
                    method,
                    currency,
                    tracker_id,
                    amount_limit,
                    amount_limit_type
                FROM tracker
                WHERE id = :id
                """,
                new MapSqlParameterSource("id", id),
                (rs, rowNum) ->
                        Tracker.builder()
                                .id(rs.getLong("id"))
                                .method(PaymentMethod.valueOf(rs.getString("method")))
                                .currency(Currency.valueOf(rs.getString("currency")))
                                .trackerId(rs.getInt("tracker_id"))
                                .amountLimit(rs.getBigDecimal("amount_limit"))
                                .amountLimitType(AmountLimitType.valueOf(rs.getString("amount_limit_type")))
                                .build()
        );
        if (trackers.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(trackers.get(0));
        }
    }

    @Override
    public Optional<Tracker[]> findAll() {
        return Optional.empty();
    }

    @Override
    public void insert(Tracker tracker) {
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                """
                INSERT INTO tracker (
                    method,
                    currency,
                    tracker_id,
                    amount_limit,
                    amount_limit_type
                ) VALUES (
                    :method,
                    :currency,
                    :tracker_id,
                    :amount_limit,
                    :amount_limit_type
                )
                """,
                new MapSqlParameterSource()
                        .addValue("method", tracker.getMethod().name())
                        .addValue("currency", tracker.getCurrency().name())
                        .addValue("tracker_id", tracker.getTrackerId())
                        .addValue("amount_limit", tracker.getAmountLimit())
                        .addValue("amount_limit_type", tracker.getAmountLimitType().name()),
                keyHolder
        );
        tracker.setId(keyHolder.getKey().longValue());
    }
}
