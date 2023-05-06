package fotius.example.auction.seller.domain;

import fotius.example.auction.api.ItemSold;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SoldItemService {
    private final SoldItemRepository soldItemRepository;
    private final SoldItemMapper soldItemMapper;

    public void saveItem(ItemSold itemSold) {
        SoldItem soldItem = soldItemMapper.map(itemSold);
        soldItemRepository.save(soldItem);
    }

    public List<SoldItem> findAll(String username) {
        return soldItemRepository.findAllBySoldBy(username);
    }
}