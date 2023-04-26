package fotius.example.donations.webhook.domain;

import fotius.example.donations.payment.domain.model.PaymentMethod;
import fotius.example.donations.payment.domain.model.PaymentState;
import fotius.example.donations.webhook.domain.model.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.net.URL;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WebhookService {

    private final WebhookRepository repository;

    @Transactional
    public  Webhook create(Long userId, URL url, PaymentMethod paymentMethod, PaymentState paymentState){
        final Webhook webhook = Webhook.builder()
                .userId(userId)
                .url(url)
                .paymentMethod(paymentMethod)
                .paymentState(paymentState)
                .build();
        repository.add(webhook);
        return webhook;
    }

    @Transactional
    public void delete(Long id){ repository.remove(id);}

    public List<Webhook> getAll(){ return repository.getAll();}

    public List<Webhook> getWithMethodAndState(Long userId,PaymentMethod paymentMethod, PaymentState paymentState){
        return repository.getWithMethodAndState(userId,paymentMethod,paymentState);
    }

    public List<Webhook> getByUserId(Long userId){return repository.getByUserId(userId);}
}
