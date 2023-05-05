package fotius.example.auction.seller.domain;

import fotius.example.auction.api.ItemSold;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SoldItemService {
    private final SoldItemRepository soldItemRepository;

    public void saveItem(ItemSold itemSold) {
        SoldItem soldItem = SoldItem
                .builder()
                .soldBy(itemSold.getSeller())
                .price(itemSold.getPrice())
                .description(itemSold.getDescription())
                .build();

        soldItemRepository.save(soldItem);
    }

    public List<SoldItem> findAll(String username) {
        return soldItemRepository.findAllSoldForUser(username);
    }

    public BigDecimal getAllIncome(String username) {
        List<SoldItem> soldItems = soldItemRepository.findAllSoldForUser(username);
        return soldItems.stream().map(SoldItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
