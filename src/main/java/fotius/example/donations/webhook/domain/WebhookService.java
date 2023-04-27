package fotius.example.donations.webhook.domain;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fotius.example.donations.payment.domain.model.PaymentMethod;
import fotius.example.donations.payment.domain.model.PaymentState;
import fotius.example.donations.webhook.domain.model.Webhook;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WebhookService {

    private final WebhookRepository repository;

    @Transactional
    public Webhook create(        
        Long userId,
        String targetUrl,
        PaymentMethod paymentMethod,
        PaymentState paymentState
    ) {
        final Webhook webhook = Webhook.builder()
            .userId(userId)
            .targetUrl(targetUrl)
            .paymentMethod(paymentMethod)
            .paymentState(paymentState)            
            .build();
        repository.insert(webhook);
        return webhook;
    }

    public void delete(        
        Long id
    ) {
        repository.delete(id);
    }

    public List<Webhook> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    public List<Webhook> getAllWebhooksByParams(PaymentMethod paymentMethod, PaymentState paymentState){
        return repository.getAllWebhooksByParams(paymentState, paymentMethod);
    }


}    

