package fotius.example.auction.buyer.presentation.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoughtItemController {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BoughtItemDto {
        private String description;
        private BigDecimal price;
    }

    @GetMapping(path = "/api/{username}/bought-items", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BoughtItemDto> findAll(@PathVariable("username") String username) {
        // TODO: change me
        return List.of(
            new BoughtItemDto("Description 1", new BigDecimal("100.0")),
            new BoughtItemDto("Description 2", new BigDecimal("200.0")),
            new BoughtItemDto("Description 3", new BigDecimal("300.0"))
        );
    }
}
