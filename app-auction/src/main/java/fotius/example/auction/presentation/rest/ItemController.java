package fotius.example.auction.presentation.rest;

import fotius.example.auction.domain.Item;
import fotius.example.auction.domain.ItemService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/items")
public class ItemController {
    
    private final ItemService itemService;
    
    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Item place(@RequestBody ItemService.PlacementCommand command) {
        return itemService.place(command);
    }

    @PostMapping(
        path = "/{id}/bids",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Item bid(@PathVariable("id") Long id, @RequestBody ItemService.BidCommand command) {
        return itemService.bid(id, command);
    }

    @PostMapping(
        path = "/{id}/closures",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void sell(@PathVariable("id") Long id) {
        itemService.sell(id);
    }

    @DeleteMapping(
        path = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public void unplace(@PathVariable("id") Long id) {
        itemService.unplace(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Item> findAll() {
        return itemService.findAll();
    }
}
