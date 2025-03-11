package myproject.shop.service;

import lombok.RequiredArgsConstructor;
import myproject.shop.domain.Item;
import myproject.shop.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Item save(Item item) throws SQLException {
        return itemRepository.save(item);
    }

    public Item findById(int itemId) throws SQLException {
        return itemRepository.findById(itemId);
    }

    public List<Item> findAll() throws SQLException {
        return itemRepository.findAll();
    }
}
