package fotius.example.auction.buyer.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoughtItem {
    @Id
    @GeneratedValue
    private Long id;
    private String boughtBy;
    private String description;
    private BigDecimal price;
}
