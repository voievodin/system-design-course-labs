package fotius.example.auction.seller.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoldItemRepository extends JpaRepository<SoldItem, Long> {
    List<SoldItem> findAllBySoldBy(String soldBy);
}