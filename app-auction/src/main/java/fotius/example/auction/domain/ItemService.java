package fotius.example.auction.domain;

import fotius.example.auction.api.ItemSold;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Validated
@RequiredArgsConstructor
public class ItemService {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PlacementCommand {

        @NotBlank(message = "Item description must be provided")
        @Size(max = 250, message = "Description lengths must not be longer than 250 chars")
        private String description;

        @NotNull
        @Positive
        private BigDecimal initialPrice;

        @NotBlank(message = "Seller username must be provided")
        @Size(min = 3, max = 30, message = "Username length must be between [3 and 30]")
        private String seller;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BidCommand {

        @NotNull
        @Positive
        private BigDecimal newPrice;

        @NotBlank(message = "Buyer username must be provided")
        @Size(min = 3, max = 30, message = "Username length must be between [3 and 30]")
        private String buyer;
    }

    private final ItemRepository repository;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public Item place(@Valid PlacementCommand command) {
        return repository.save(
            Item.builder()
                .description(command.description)
                .initialPrice(command.initialPrice)
                .currentPrice(command.initialPrice)
                .seller(command.seller)
                .placedAt(LocalDateTime.now())
                .build()
        );
    }

    public Item getById(Long id) {
        return repository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Auction item with id '%d' not found".formatted(id))
        );
    }
    
    @Transactional
    public Item bid(Long id, @Valid BidCommand command) {
        Item item = getById(id);
        if (command.getNewPrice().compareTo(item.getCurrentPrice()) < 0) {
            throw new ValidationException(
                "Expected new price to be greater than the current price, while %s < %s".formatted(
                    command.newPrice,
                    item.getCurrentPrice()
                )
            );
        } else if (command.getNewPrice().compareTo(item.getCurrentPrice()) == 0 && item.getBuyer() != null) {
            throw new ValidationException("Expected new price to be greater than the current price, while the price is the same");
        }
        if (command.getBuyer().equals(item.getSeller())) {
            throw new ValidationException(
                "Buyer cannot by it's own items, placed by '%s', bid by '%s'".formatted(
                    item.getSeller(), 
                    command.getBuyer()
                )
            );
        }
        item.setCurrentPrice(command.newPrice);
        item.setBuyer(command.buyer);
        return item;
    }
    
    @Transactional
    public void sell(Long id) {
        final Item item = getById(id);
        if (item.getBuyer() == null) {
            throw new ValidationException("Cannot make item without buyer sold, consider removing it from sails");
        }
        repository.delete(item);
        publisher.publishEvent(
            ItemSold.builder()
                .eventId(UUID.randomUUID().toString())
                .description(item.getDescription())
                .price(item.getCurrentPrice())
                .buyer(item.getBuyer())
                .seller(item.getSeller())
                .soldAt(LocalDateTime.now())
                .build()
        );
    }

    @Transactional
    public void unplace(Long id) {
        repository.findById(id).ifPresent(repository::delete);
    }

    public List<Item> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "placedAt"));
    }
}
