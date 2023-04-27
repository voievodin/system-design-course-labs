package fotius.example.donations.webhook.presentation.rest;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fotius.example.donations.payment.domain.model.PaymentMethod;
import fotius.example.donations.payment.domain.model.PaymentState;
import fotius.example.donations.webhook.domain.WebhookService;
import fotius.example.donations.webhook.domain.model.Webhook;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/webhooks")
public class WebhookController {

    private WebhookService webhookService;
    
    
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Webhook create(
        @RequestParam("userId") Long userId,
        @RequestParam("targetUrl") String targetUrl,
        @RequestParam("paymentMethod") PaymentMethod paymentMethod,
        @RequestParam("paymentState") PaymentState paymentState
    ) {
        return webhookService.create(
            userId,
            targetUrl,
            paymentMethod,
            paymentState
        );
    }

    @PostMapping(
        path = "/delete",
        produces = MediaType.APPLICATION_JSON_VALUE
        )
    @ResponseBody
    public void delete(@RequestParam("id") Long id) {
        webhookService.delete(id);
    }

    @GetMapping(
        path = "/{user_id}",
        produces = MediaType.APPLICATION_JSON_VALUE
        )
    @ResponseBody
    public List<Webhook> findByUserId(
        @PathVariable("user_id") Long userId
    ) {
        return webhookService.findByUserId(userId);
    }
}
