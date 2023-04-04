package fotius.example.donations.payment.domain;

import fotius.example.donations.payment.domain.model.PaymentMethod;
import fotius.example.donations.payment.domain.model.PaymentMethodTypes;

import java.util.List;

public interface PaymentMethodRepository {

    List<PaymentMethod> getMethods(Long userId);

    void insert(PaymentMethod method);

    void update(PaymentMethod method);
}
