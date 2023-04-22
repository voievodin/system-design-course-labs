package fotius.example.donations.account.presentation.rest;

import fotius.example.donations.account.domain.ExchangeService;
import fotius.example.donations.common.model.Currency;
import fotius.example.donations.account.domain.model.ExchangeRate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/exchange")
public class ExchangeController {

    private final ExchangeService exchangeService;

    @GetMapping
    public ExchangeRate getExchangeRate(@RequestParam("from") Currency from, @RequestParam("to") Currency to) {
        return exchangeService.getExchangeRate(from, to);
    }
}
