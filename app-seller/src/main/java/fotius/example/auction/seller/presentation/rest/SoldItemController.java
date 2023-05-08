package fotius.example.auction.seller.presentation.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import fotius.example.auction.seller.domain.SoldItem;
import fotius.example.auction.seller.domain.SoldItemService;
import lombok.*;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
import java.util.List;
@RequiredArgsConstructor
@RestController
public class SoldItemController {

    private final SoldItemService soldItemService;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SoldItemDto {
        private String description;
        private BigDecimal price;
    }

    @GetMapping(path = "/api/{username}/sold-items", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SoldItemDto> findAll(@PathVariable("username") String username) {
        List<SoldItem> soldItems = soldItemService.findAll(username);
        return soldItems
                .stream()
                .map(item ->
                        SoldItemDto
                                .builder()
                                .description(item.getDescription())
                                .price(item.getPrice())
                                .build())
                .toList();
    }

    @GetMapping(path = "/api/{username}/get-all-income", produces = MediaType.APPLICATION_JSON_VALUE)
    public BigDecimal getAllIncome(@PathVariable("username") String username) {
        return soldItemService.getAllIncome(username);
    }
}