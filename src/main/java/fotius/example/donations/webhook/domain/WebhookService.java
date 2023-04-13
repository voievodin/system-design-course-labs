package fotius.example.donations.webhook.domain;

import fotius.example.donations.payment.domain.model.PaymentMethod;
import fotius.example.donations.payment.domain.model.PaymentState;
import fotius.example.donations.webhook.domain.model.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WebhookService {

    private final WebhookRepository repository;

    @Transactional
    public Webhook create(
            Long userId,
            URL targetUrl,
            PaymentMethod paymentMethod,
            PaymentState paymentState
    ) {
        final Webhook webhook = Webhook.builder()
                .userId(userId)
                .targetUrl(targetUrl)
                .paymentMethod(paymentMethod)
                .paymentState(paymentState)
                .createdAt(LocalDateTime.now())
                .build();
        repository.insert(webhook);
        return webhook;
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(id);
    }

    public List<Webhook> getAll() {
        return repository.getAll();
    }

    public List<Webhook> getWithMethodAndState(PaymentMethod method, PaymentState state) {
        return repository.getWithMethodAndState(method, state);
    }

    public List<Webhook> getAllByUserId(Long userId) {
        return repository.getAllByUserId(userId);
    }
}
