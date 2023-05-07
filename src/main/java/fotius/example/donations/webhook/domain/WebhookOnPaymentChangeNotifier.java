package fotius.example.donations.webhook.domain;

import fotius.example.donations.payment.domain.PaymentChangeChecker;
import fotius.example.donations.payment.domain.model.Payment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WebhookOnPaymentChangeNotifier implements PaymentChangeChecker {

    private final WebhookService webhookService;
    @Override
    public void onChange(Payment changed) {
        webhookService
                .getWithMethodAndState(changed.getMethod(), changed.getState())
                .forEach(x -> {
                    System.out.println("Payment " + changed.getId() + " changed. "
                            + "Payment method: " + changed.getMethod() + "; "
                            + "Payment state: " + changed.getState() + ";");
                });
    }
}
