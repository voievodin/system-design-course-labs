package fotius.example.auction.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue
    private Long id;
    private String description;
    private BigDecimal initialPrice;
    private BigDecimal currentPrice;
    private String seller;
    private String buyer;
    private LocalDateTime placedAt;
    @Version
    private int version;
}
