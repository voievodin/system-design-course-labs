package fotius.example.donations.payment.domain.client;

import fotius.example.donations.payment.domain.model.BalanceChangedEvent;

public interface AccountServiceClient {
    void updateBalance(long accountId, BalanceChangedEvent balanceChangedEvent);
}
