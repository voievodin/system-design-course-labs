package fotius.example.donations.monitoring.presentation.rest;

import fotius.example.donations.monitoring.domain.TrackerService;
import fotius.example.donations.monitoring.domain.model.AmountLimitType;
import fotius.example.donations.monitoring.domain.model.PaymentsSuccessRate;
import fotius.example.donations.monitoring.domain.model.Tracker;
import fotius.example.donations.payment.domain.model.Currency;
import fotius.example.donations.payment.domain.model.Payment;
import fotius.example.donations.payment.domain.model.PaymentMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/trackers")
public class TrackerController {

    private final TrackerService trackerService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Tracker create(
        @RequestParam("method") PaymentMethod method,
        @RequestParam("currency") Currency currency,
        @RequestParam("amount_limit") BigDecimal amountLimit,
        @RequestParam("amount_limit_type") AmountLimitType amountLimitType,
        @RequestParam("tracker_id") int trackerId
    ) {
        System.out.println("sdjfsdlfi");
        return trackerService.create(trackerId,
                amountLimitType,
                amountLimit,
                currency,
                method);
    }

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Tracker getById(@PathVariable("id") int id) {
        return trackerService.getById(id);
    }

    @GetMapping(
            path = "/rate/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public PaymentsSuccessRate getSuccessRate(@PathVariable("id") int id) { return trackerService.getSuccessRateById(id); }
}
