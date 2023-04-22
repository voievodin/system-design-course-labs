package fotius.example.donations.payment.presentation.rest;

import fotius.example.donations.common.model.Currency;
import fotius.example.donations.payment.domain.PaymentService;
import fotius.example.donations.payment.domain.model.Payment;
import fotius.example.donations.payment.domain.model.PaymentMethod;
import fotius.example.donations.payment.domain.model.PaymentState;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Payment create(
        @RequestParam("method") PaymentMethod method,
        @RequestParam("currency") Currency currency,
        @RequestParam("amount") BigDecimal amount,
        @RequestParam("user_id") Long userId
    ) {
        return paymentService.create(
            amount,
            currency,
            method,
            userId
        );
    }

    @PostMapping(
        path = "/{id}/state/{newState}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Payment changeState(
        @PathVariable("id") Long id,
        @PathVariable("newState") PaymentState state
    ) {
        return paymentService.changeState(id, state);
    }

    @GetMapping(
        path = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Payment getById(@PathVariable("id") Long id) {
        return paymentService.getById(id);
    }
}
