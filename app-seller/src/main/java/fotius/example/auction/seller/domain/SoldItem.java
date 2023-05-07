package fotius.example.auction.seller.domain;
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
public class SoldItem {
    @Id
    @GeneratedValue
    private Long id;
    private String soldBy;
    private String description;
    private BigDecimal price;
}
