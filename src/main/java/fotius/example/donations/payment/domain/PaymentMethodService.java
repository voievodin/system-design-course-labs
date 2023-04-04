package fotius.example.donations.payment.domain;

import fotius.example.donations.payment.domain.model.PaymentMethod;
import fotius.example.donations.payment.domain.model.PaymentMethodTypes;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PaymentMethodService {
    private final PaymentMethodRepository repository;


    // controller methods
    public List<PaymentMethodTypes> getMethods(Long userId) {
        List<PaymentMethod> methods = getAvailableMethods(userId);
        return methods.stream().map(PaymentMethod::getType).collect(Collectors.toList());
    }
    public void unblockPaymentMethod(PaymentMethodTypes method, Long userId) {
        PaymentMethod paymentMethod = getMethodByType(method, userId);
        paymentMethod.setAvailable(true);
        paymentMethod.setAvailability_timestamp_from(null);
        repository.update(paymentMethod);
    }
    public void blockPaymentMethod(PaymentMethodTypes method, Long userId) {
        blockPaymentMethod(method, userId, null);
    }
    public void blockPaymentMethod(PaymentMethodTypes method, Long userId, LocalDateTime timestamp) {
        PaymentMethod paymentMethod = getMethodByType(method, userId);
        paymentMethod.setAvailable(false);
        paymentMethod.setAvailability_timestamp_from(timestamp);
        repository.update(paymentMethod);
    }
    public void blockAllPaymentMethods(Long userId) {
        List<PaymentMethod> methods = repository.getMethods(userId);
        for (PaymentMethod method : methods) {
            method.setAvailable(false);
            method.setAvailability_timestamp_from(null);
            repository.update(method);
        }
    }
    public void createOrUpdatePaymentMethodsForUser(Long userId) {
        List<PaymentMethod> methods = repository.getMethods(userId);
        if (methods.isEmpty()) {
            for (PaymentMethodTypes type : PaymentMethodTypes.values()) {
                repository.insert(PaymentMethod.builder()
                        .userId(userId)
                        .type(type)
                        .isAvailable(true)
                        .availability_timestamp_from(null)
                        .build());
            }
        }
    }


    // auxiliary methods
    private PaymentMethod getMethodByType(PaymentMethodTypes method, Long userId) {
        var methods = getAllMethods(userId);
        return methods.stream().filter(m -> m.getType().equals(method)).findFirst().orElseThrow();
    }
    private List<PaymentMethod> getAvailableMethods(Long userId) {
        return getAllMethods(userId).stream().filter(PaymentMethod::isAvailable).collect(Collectors.toList());
    }
    private List<PaymentMethod> getAllMethods(Long userId) {
        updateIfSomeMethodAvailableByTimestamp(userId);
        return repository.getMethods(userId);
    }
    private void updateIfSomeMethodAvailableByTimestamp(Long userId) {
        List<PaymentMethod> methods = repository.getMethods(userId);
        for (PaymentMethod method : methods) {
            if (!method.isAvailable()
                    && method.getAvailability_timestamp_from() != null
                    && method.getAvailability_timestamp_from().isBefore(LocalDateTime.now())) {

                method.setAvailable(true);
                method.setAvailability_timestamp_from(null);
                repository.update(method);
            }
        }
    }
}
