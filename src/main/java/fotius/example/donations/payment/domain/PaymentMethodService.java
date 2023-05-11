package fotius.example.donations.payment.domain;

import fotius.example.donations.payment.domain.model.PaymentMethod;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentMethodService {
    public List<PaymentMethod> getMethods(Long userId) {
        return List.of(
            PaymentMethod.CARD,
            PaymentMethod.APPLE_PAY,
            PaymentMethod.GOOGLE_PAY
        );
    }
}
