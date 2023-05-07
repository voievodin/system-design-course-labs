package fotius.example.donations.webhook.domain;

import fotius.example.donations.payment.domain.PaymentChangeListener;
import fotius.example.donations.payment.domain.model.Payment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AccessWebhooksOnPaymentStateChange implements PaymentChangeListener {

    private final WebhookService webhookService;

    @Override
    public void onChanged(Payment changed) {
        webhookService.getAllByMethodAndState(changed.getMethod(), changed.getState())
                .forEach(webhook -> System.out.println("Send message on " + webhook.getTargetUrl()));
    }
}
