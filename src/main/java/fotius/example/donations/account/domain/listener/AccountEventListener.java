package fotius.example.donations.account.domain.listener;

import fotius.example.donations.account.domain.AccountService;
import fotius.example.donations.account.domain.model.ChangeBalanceCommand;
import fotius.example.donations.payment.domain.model.PaymentCompletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountEventListener {

    private final AccountService accountService;

    @EventListener(PaymentCompletedEvent.class)
    public void handlePaymentCompletedEvent(PaymentCompletedEvent event) {
        accountService.updateBalance(ChangeBalanceCommand.builder()
                .accountId(event.getPayment().getUserId())
                .amount(event.getPayment().getAmount())
                .currency(event.getPayment().getCurrency())
                .build());
    }
}
