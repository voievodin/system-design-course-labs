package fotius.example.auction.buyer.presentation.kafka;

import fotius.example.auction.api.ItemSold;
import fotius.example.auction.buyer.domain.BoughtItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ItemSoldKafkaConsumer {
    
    private final ObjectMapper objectMapper;
    private final BoughtItemService boughtItemService;
    
    @KafkaListener(topics = ItemSold.TOPIC)
    public void onItemSold(String payload) throws JsonProcessingException {
        final ItemSold itemSold = objectMapper.readValue(payload, ItemSold.class);
        log.debug("Item sold: {}", itemSold);
        boughtItemService.saveItem(itemSold);
    }
}
