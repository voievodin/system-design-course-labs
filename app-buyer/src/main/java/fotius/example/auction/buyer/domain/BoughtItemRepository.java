package fotius.example.auction.buyer.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoughtItemRepository extends JpaRepository<BoughtItem, Long> {

    List<BoughtItem> findAllByBoughtBy(String boughtBy);
}
