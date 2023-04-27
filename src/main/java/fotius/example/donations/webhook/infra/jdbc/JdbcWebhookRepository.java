package fotius.example.donations.webhook.infra.jdbc;


import fotius.example.donations.payment.domain.model.PaymentMethod;
import fotius.example.donations.payment.domain.model.PaymentState;
import fotius.example.donations.webhook.domain.WebhookRepository;
import fotius.example.donations.webhook.domain.model.Webhook;
import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class JdbcWebhookRepository implements WebhookRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    

    @Override
    public void insert(Webhook webhook) {
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
            """
            INSERT INTO webhook (
                id,
                target_url,
                payment_method,
                payment_state,
                user_id        
            ) VALUES (
                :id,
                :target_url,
                :payment_method,
                :payment_state,
                :user_id   
            )
            """,
            new MapSqlParameterSource()
                .addValue("id", webhook.getId())
                .addValue("target_url", webhook.getTargetUrl())
                .addValue("payment_method", webhook.getPaymentMethod())
                .addValue("payment_state", webhook.getPaymentState())
                .addValue("user_id", webhook.getUserId()),                
            keyHolder
        );
        webhook.setId(keyHolder.getKey().longValue());
    }



    @Override
    public void delete(Long id) {
        jdbcTemplate.update(
            """
            DELETE FROM webhook WHERE id=:id
            """,
            new MapSqlParameterSource()
                .addValue("id", id)                
        );
    }

    @Override
    public List<Webhook> findByUserId(Long userId) {
        return jdbcTemplate.query(
            """
            SELECT * FROM webhook
            WHERE user_id = :user_id
            """,
            new MapSqlParameterSource("user_id", userId),
            (rs, rowNum) ->
                Webhook.builder()
                    .id(rs.getLong("id"))
                    .targetUrl(rs.getString("target_url"))
                    .paymentMethod(PaymentMethod.valueOf(rs.getString("payment_method")))
                    .paymentState(PaymentState.valueOf(rs.getString("payment_state")))
                    .userId(rs.getLong("user_id"))
                    .build()
        );
    }



    @Override
    public List<Webhook> getAllWebhooksByParams(PaymentState paymentState, PaymentMethod paymentMethod) {
        return jdbcTemplate.query(
            """
            SELECT * FROM webhook 
                WHERE payment_method= :payment_method AND payment_state= :payment_state 
            """,
            new MapSqlParameterSource()
                .addValue("payment_method", paymentMethod)
                .addValue("payment_state", paymentState),
            (rs, rowNum) ->
            Webhook.builder()
                .id(rs.getLong("id"))
                .targetUrl(rs.getString("target_url"))
                .paymentMethod(PaymentMethod.valueOf(rs.getString("payment_method")))
                .paymentState(PaymentState.valueOf(rs.getString("payment_state")))
                .userId(rs.getLong("user_id"))
                .build()
        );
    }
    
}
