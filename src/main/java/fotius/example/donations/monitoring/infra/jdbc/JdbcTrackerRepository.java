package fotius.example.donations.monitoring.infra.jdbc;

import fotius.example.donations.monitoring.domain.TrackerRepository;
import fotius.example.donations.monitoring.domain.model.AmountLimitType;
import fotius.example.donations.monitoring.domain.model.Tracker;
import fotius.example.donations.payment.domain.model.Currency;
import fotius.example.donations.payment.domain.model.PaymentMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class JdbcTrackerRepository implements TrackerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;


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
                """,
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
            Tracker[] result = new Tracker[trackers.size()];
            trackers.toArray(result);
            return Optional.of(result);
        }
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
