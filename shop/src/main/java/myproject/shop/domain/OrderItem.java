package myproject.shop.domain;

import lombok.Data;

@Data
public class OrderItem {

    private int orderId;
    private int itemId;
    private int orderPrice;
    private int quantity;

    public OrderItem(int itemId, int quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }
}
