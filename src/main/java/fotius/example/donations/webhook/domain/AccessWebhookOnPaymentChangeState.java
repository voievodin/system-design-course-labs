package fotius.example.donations.webhook.domain;

import java.util.List;

import org.springframework.stereotype.Component;

import fotius.example.donations.payment.domain.PaymentChangeListener;
import fotius.example.donations.payment.domain.model.Payment;
import fotius.example.donations.webhook.domain.model.Webhook;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
class AccessWebhookOnPaymentChangeState implements PaymentChangeListener{

    final WebhookService webhookService;
    List <Webhook> webhooks;

    @Override
    public void onChange(Payment payment) {
        webhooks = webhookService.getAllWebhooksByParams(payment.getMethod(),payment.getState()); 
        for (Webhook webhook : webhooks) {
            System.out.println(webhook.getTargetUrl());
        }
    }
    
}