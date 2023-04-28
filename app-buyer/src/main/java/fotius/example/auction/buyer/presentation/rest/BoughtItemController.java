package fotius.example.auction.buyer.presentation.rest;

import fotius.example.auction.buyer.domain.BoughtItem;
import fotius.example.auction.buyer.domain.BoughtItemService;
import fotius.example.auction.buyer.presentation.rest.dto.BoughtItemDto;
import fotius.example.auction.buyer.presentation.rest.dto.mapper.BoughtItemDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoughtItemController {

    private final BoughtItemService boughtItemService;
    private final BoughtItemDtoMapper boughtItemDtoMapper;

    @GetMapping(path = "/api/{username}/bought-items", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BoughtItemDto> findAll(@PathVariable("username") String username) {
        List<BoughtItem> boughtItems = boughtItemService.findAll(username);
        return boughtItemDtoMapper.map(boughtItems);
    }
}
