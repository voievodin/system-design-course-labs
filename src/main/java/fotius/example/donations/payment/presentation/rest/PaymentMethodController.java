package fotius.example.donations.payment.presentation.rest;

import fotius.example.donations.payment.domain.PaymentMethodService;
import fotius.example.donations.payment.domain.model.PaymentMethodTypes;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping(path = "/{userId}/unblock/{method}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void unblockPaymentMethod(@PathVariable("userId") Long userId, @PathVariable("method") PaymentMethodTypes method) {
        paymentMethodService.unblockPaymentMethod(method, userId);
    }

    @GetMapping(path = "/{userId}/block/{method}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void blockPaymentMethod(@PathVariable("userId") Long userId, @PathVariable("method") PaymentMethodTypes method) {
        paymentMethodService.blockPaymentMethod(method, userId);
    }

    @GetMapping(path = "/{userId}/blockAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void blockAllPaymentMethods(@PathVariable("userId") Long userId) {
        paymentMethodService.blockAllPaymentMethods(userId);
    }

    @GetMapping(path = "/{userId}/createOrUpdate", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void createOrUpdatePaymentMethodsForUser(@PathVariable("userId") Long userId) {
        paymentMethodService.createOrUpdatePaymentMethodsForUser(userId);
    }
}
