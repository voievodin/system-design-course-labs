package fotius.example.donations.webhook.domain;

import java.util.List;

import fotius.example.donations.payment.domain.model.PaymentMethod;
import fotius.example.donations.payment.domain.model.PaymentState;
import fotius.example.donations.webhook.domain.model.Webhook;

public interface WebhookRepository {

    void insert(Webhook webhook);

    void delete (Long id);

    List<Webhook> findByUserId(Long userId);

    List<Webhook> getAllWebhooksByParams(PaymentState paymentState, PaymentMethod paymentMethod);
}    

