package myproject.shop.domain;

import lombok.Data;

@Data
public class Item {
    private int itemId;
    private int stockQuantity;
    private int price;

    public Item() {
    }

    public Item(int stockQuantity, int price) {
        this.stockQuantity = stockQuantity;
        this.price = price;
    }
}
