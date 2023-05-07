package fotius.example.donations.payment.domain;

import fotius.example.donations.payment.domain.model.Payment;

public interface PaymentChangeChecker {
    void onChange(Payment changed);
}
