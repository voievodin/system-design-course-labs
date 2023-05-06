package fotius.example.auction.seller.presentation.rest;

import fotius.example.auction.seller.domain.SoldItem;
import fotius.example.auction.seller.domain.SoldItemService;
import lombok.*;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class SoldItemController {

    private final SoldItemService soldItemService;
    private final SoldItemDtoMapper soldItemDtoMapper;

    @GetMapping(path = "/api/{username}/sold-items", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SoldItemDto> findAll(@PathVariable("username") String username) {
        List<SoldItem> soldItems = soldItemService.findAll(username);
        return soldItemDtoMapper.map(soldItems);
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SoldItemDto {
        private String description;
        private BigDecimal price;
    }

    @Component
    public static class SoldItemDtoMapper {

        public List<SoldItemDto> map(List<SoldItem> soldItems) {
            return soldItems.stream()
                    .map(this::map)
                    .collect(Collectors.toList());
        }

        public SoldItemDto map(SoldItem soldItem) {
            return SoldItemDto.builder()
                    .description(soldItem.getDescription())
                    .price(soldItem.getPrice())
                    .build();
        }
    }
}