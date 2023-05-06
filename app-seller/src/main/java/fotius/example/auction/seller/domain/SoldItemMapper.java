package fotius.example.auction.seller.domain;

import fotius.example.auction.api.ItemSold;
import org.springframework.stereotype.Component;

@Component
public class SoldItemMapper {
    public SoldItem map(ItemSold event) {
        return SoldItem.builder()
                .soldBy(event.getSeller())
                .description(event.getDescription())
                .price(event.getPrice())
                .build();
    }
}