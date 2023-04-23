package fotius.example.auction.seller.presentation.kafka;

import fotius.example.auction.api.ItemSold;
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
    
    @KafkaListener(topics = ItemSold.TOPIC)
    public void onItemSold(String payload) throws JsonProcessingException {
        final ItemSold itemSold = objectMapper.readValue(payload, ItemSold.class);
        log.info("Item sold: {}", itemSold);
        // TODO: add code to actually process event
    }
}
