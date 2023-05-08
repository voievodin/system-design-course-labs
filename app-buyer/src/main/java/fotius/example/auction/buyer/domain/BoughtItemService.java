package fotius.example.auction.buyer.domain;

import fotius.example.auction.api.ItemSold;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoughtItemService {
    private final BoughtItemRepository boughtItemRepository;

    public void save(ItemSold item) {
        var boughtItem = BoughtItem.builder()
                .buyerUsername(item.getBuyer())
                .description(item.getDescription())
                .price(item.getPrice())
                .build();
        boughtItemRepository.save(boughtItem);
    }

    public List<BoughtItem> findAllByUsername(String username) {
        return boughtItemRepository.findByBuyerUsername(username);
    }
}
