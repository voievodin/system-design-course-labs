package fotius.example.auction.buyer.domain;

import fotius.example.auction.api.ItemSold;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoughtItemService {

    private final BoughtItemRepository boughtItemRepository;
    private final BoughtItemMapper boughtItemMapper;

    public void saveItem(ItemSold itemSold) {
        BoughtItem boughtItem = boughtItemMapper.map(itemSold);
        boughtItemRepository.save(boughtItem);
    }

    public List<BoughtItem> findAll(String username) {
        return boughtItemRepository.findAllByBoughtBy(username);
    }
}
