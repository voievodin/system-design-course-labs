package fotius.example.auction.seller.domain;

import fotius.example.auction.seller.presentation.rest.SoldItemController;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SoldItemRepository extends JpaRepository<SoldItem, Long> {
    List<SoldItem> findAllSoldForUser(String soldBy);
}
