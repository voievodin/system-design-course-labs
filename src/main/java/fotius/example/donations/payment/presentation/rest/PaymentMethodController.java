package fotius.example.donations.payment.presentation.rest;

import fotius.example.donations.payment.domain.services.PaymentMethodService;
import fotius.example.donations.payment.domain.model.PaymentMethodTypes;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/methods")
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    @GetMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<PaymentMethodTypes> getMethods(@PathVariable("userId") Long userId) {
        return paymentMethodService.getMethods(userId);
    }

    @PatchMapping(path = "/{userId}/unblock/{method}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public PaymentMethodTypes unblockPaymentMethod(@PathVariable("userId") Long userId, @PathVariable("method") PaymentMethodTypes method) {
        return paymentMethodService.unblockPaymentMethod(method, userId);
    }

    @PatchMapping(path = "/{userId}/block/{method}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public PaymentMethodTypes blockPaymentMethod(@PathVariable("userId") Long userId, @PathVariable("method") PaymentMethodTypes method) {
        return paymentMethodService.blockPaymentMethod(method, userId);
    }

    @PatchMapping(path = "/{userId}/blockAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<PaymentMethodTypes> blockAllPaymentMethods(@PathVariable("userId") Long userId) {
        return paymentMethodService.blockAllPaymentMethods(userId);
    }

    @PatchMapping(path = "/{userId}/unblockAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<PaymentMethodTypes> unblockAllPaymentMethods(@PathVariable("userId") Long userId) {
        return paymentMethodService.unblockAllPaymentMethods(userId);
    }

    @PostMapping(path = "/{userId}/init", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<PaymentMethodTypes> createOrUpdatePaymentMethodsForUser(@PathVariable("userId") Long userId) {
        return paymentMethodService.createOrUpdatePaymentMethodsForUser(userId);
    }
}
