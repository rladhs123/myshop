package myproject.shop.repository;

import myproject.shop.domain.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @Test
    void save() throws SQLException {
        Item item = new Item(100, 10000);

        Item savedItem = itemRepository.save(item);

        assertThat(savedItem.getItemId()).isEqualTo(4);
    }
}