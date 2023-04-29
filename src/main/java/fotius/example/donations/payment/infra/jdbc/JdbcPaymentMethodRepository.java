package fotius.example.donations.payment.infra.jdbc;

import fotius.example.donations.payment.domain.repositories.PaymentMethodRepository;
import fotius.example.donations.payment.domain.model.PaymentMethod;
import fotius.example.donations.payment.domain.model.PaymentMethodTypes;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JdbcPaymentMethodRepository implements PaymentMethodRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<PaymentMethod> getMethods(Long userId) {
        return jdbcTemplate.query(
                """
                SELECT
                    id,
                    user_id,
                    name,
                    is_available,
                    availability_timestamp_from
                FROM payment_method
                WHERE user_id = :user_id
                """,
                new MapSqlParameterSource("user_id", userId),
                (rs, rowNum) ->
                        PaymentMethod.builder()
                            .id(rs.getLong("id"))
                            .userId(rs.getLong("user_id"))
                            .type(PaymentMethodTypes.valueOf(rs.getString("name")))
                            .isAvailable(rs.getBoolean("is_available"))
                            .availability_timestamp_from(rs.getTimestamp("availability_timestamp_from") == null ? null
                                    : rs.getTimestamp("availability_timestamp_from").toLocalDateTime())
                            .build()
        );
    }

    @Override
    public void insert(PaymentMethod method) {
        jdbcTemplate.update(
                """
                INSERT INTO payment_method
                (
                    user_id,
                    name,
                    is_available,
                    availability_timestamp_from
                )
                VALUES
                (
                    :user_id,
                    :name,
                    :is_available,
                    :availability_timestamp_from
                )
                """,
                new MapSqlParameterSource()
                        .addValue("user_id", method.getUserId())
                        .addValue("name", method.getType().name())
                        .addValue("is_available", method.isAvailable())
                        .addValue("availability_timestamp_from", method.getAvailability_timestamp_from() == null ? null
                                : Timestamp.valueOf(method.getAvailability_timestamp_from()))
        );
    }

    @Override
    public void update(PaymentMethod method) {
        jdbcTemplate.update(
                """
                UPDATE payment_method
                SET
                    user_id = :user_id,
                    name = :name,
                    is_available = :is_available,
                    availability_timestamp_from = :availability_timestamp_from
                WHERE id = :id
                """,
                new MapSqlParameterSource()
                        .addValue("id", method.getId())
                        .addValue("user_id", method.getUserId())
                        .addValue("name", method.getType().name())
                        .addValue("is_available", method.isAvailable())
                        .addValue("availability_timestamp_from", method.getAvailability_timestamp_from() == null ? null
                                : Timestamp.valueOf(method.getAvailability_timestamp_from()))
        );
    }
}