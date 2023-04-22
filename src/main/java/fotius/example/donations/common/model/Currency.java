package fotius.example.donations.common.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Currency {

    UAH(1.0),
    EUR(40.1066),
    USD(36.5686);

    private final double exchangeRate;
}
