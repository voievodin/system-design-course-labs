package fotius.example.auction.buyer.presentation.rest;

import fotius.example.auction.buyer.domain.BoughtItemService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class BoughtItemController {

    private final BoughtItemService boughtItemService;

    @GetMapping(path = "/api/{username}/bought-items", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BoughtItemDto> findAll(@PathVariable("username") String username) {

        return boughtItemService.findAllByUsername(username).stream()
                .map(x -> BoughtItemDto.builder()
                        .description(x.getDescription())
                        .price(x.getPrice())
                        .build())
                .collect(Collectors.toList());
    }
}
