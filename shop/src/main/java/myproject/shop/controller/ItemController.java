package myproject.shop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import myproject.shop.domain.Item;
import myproject.shop.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items")
    public List<Item> items() throws SQLException {
        return itemService.findAll();
    }

    @GetMapping("/items/{itemId}")
    public Item findItem(@PathVariable("itemId") int itemId) throws SQLException {
        return itemService.findById(itemId);
    }

    @PostMapping("/items/new")
    public Item saveItem(@RequestBody @Valid Item item) throws SQLException {
        return itemService.save(item);
    }
}
