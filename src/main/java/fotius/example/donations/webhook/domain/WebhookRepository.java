package fotius.example.donations.webhook.domain;

import fotius.example.donations.payment.domain.model.PaymentMethod;
import fotius.example.donations.payment.domain.model.PaymentState;
import fotius.example.donations.webhook.domain.model.Webhook;

import java.util.List;

public interface WebhookRepository {
    void add(Webhook webhook);

    void remove(Long id);

    List<Webhook> getAll();

    List<Webhook> getWithMethodAndState(Long userId,PaymentMethod paymentMethod, PaymentState paymentState);

    List<Webhook> getByUserId(Long userId);
}
