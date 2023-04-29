package fotius.example.donations.payment.domain;

import fotius.example.donations.payment.domain.model.Payment;

public interface PaymentChangeListener {
    void onChanged(Payment changed);
}
