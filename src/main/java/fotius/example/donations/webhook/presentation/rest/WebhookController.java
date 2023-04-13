package fotius.example.donations.webhook.presentation.rest;

import fotius.example.donations.payment.domain.model.PaymentMethod;
import fotius.example.donations.payment.domain.model.PaymentState;
import fotius.example.donations.webhook.domain.WebhookService;
import fotius.example.donations.webhook.domain.model.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Scope()
@RequestMapping("/api/webhooks")
public class WebhookController {

    private final WebhookService webhookService;

    @PostMapping(
            path = "/{user_id}/add",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Webhook create(
            @PathVariable("user_id") Long userId,
            @RequestParam("target_url") URL targetUrl,
            @RequestParam("payment_method") PaymentMethod paymentMethod,
            @RequestParam ("payment_state")PaymentState paymentState
    ) {
        return webhookService.create(
                userId,
                targetUrl,
                paymentMethod,
                paymentState
        );
    }

    @PostMapping(
            path = "/remove/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public void remove(
            @PathVariable("id") Long id
    ) {
        webhookService.delete(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Webhook> getAll() {
        return webhookService.getAll();
    }

    @GetMapping(
            path = "/{user_id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public List<Webhook> getAllByUserId(
            @PathVariable("user_id") Long userId
    ) {
        return webhookService.getAllByUserId(userId);
    }
}
