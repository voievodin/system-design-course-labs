package fotius.example.donations.payment.presentation.rest;

import fotius.example.donations.payment.domain.PaymentMethodService;
import fotius.example.donations.payment.domain.model.PaymentMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/methods")
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    @GetMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<PaymentMethod> getMethods(@PathVariable("userId") Long userId) {
        return paymentMethodService.getMethods(userId);
    }
}
