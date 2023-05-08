package fotius.example.auction.presentation.kafka;

import fotius.example.auction.api.ItemSold;
import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemSoldKafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    
    @EventListener(ItemSold.class)
    public void sendItemSoldEvent(ItemSold itemSold) throws JsonProcessingException {
        kafkaTemplate.send(
            ItemSold.TOPIC, 
            objectMapper.writer().writeValueAsString(itemSold)
        );
    }
}
