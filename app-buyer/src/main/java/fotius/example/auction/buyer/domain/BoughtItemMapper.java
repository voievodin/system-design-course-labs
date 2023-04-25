package fotius.example.auction.buyer.domain;

import fotius.example.auction.api.ItemSold;
import org.springframework.stereotype.Component;

@Component
public class BoughtItemMapper {

    public BoughtItem map(ItemSold event) {
        return BoughtItem.builder()
                .boughtBy(event.getBuyer())
                .description(event.getDescription())
                .price(event.getPrice())
                .build();
    }
}
