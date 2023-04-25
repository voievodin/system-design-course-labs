package fotius.example.auction.buyer.presentation.rest.dto;

import fotius.example.auction.buyer.domain.BoughtItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BoughtItemDtoMapper {

    public List<BoughtItemDto> map(List<BoughtItem> boughtItems) {
        return boughtItems.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public BoughtItemDto map(BoughtItem boughtItem) {
        return BoughtItemDto.builder()
                .description(boughtItem.getDescription())
                .price(boughtItem.getPrice())
                .build();
    }
}
