package fotius.example.donations.payment.infra.rest;

import fotius.example.donations.payment.domain.model.BalanceChangedEvent;
import fotius.example.donations.payment.domain.client.AccountServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AccountServiceRestClient implements AccountServiceClient {

    private static final String BALANCE_URL = "http://localhost:8080/api/accounts/{accountId}/balance";

    private final RestTemplate restTemplate;

    @Override
    public void updateBalance(long accountId, BalanceChangedEvent balanceChangedEvent) {
        restTemplate.put(BALANCE_URL, balanceChangedEvent, accountId);
    }
}
