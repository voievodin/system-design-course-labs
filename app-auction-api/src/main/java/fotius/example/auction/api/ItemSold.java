package fotius.example.auction.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemSold {
    
    public static final String TOPIC = "app-auction.out.ItemSold";

    private String eventId;
    private String description;
    private BigDecimal price;
    private String seller;
    private String buyer;
    private LocalDateTime soldAt;
}
