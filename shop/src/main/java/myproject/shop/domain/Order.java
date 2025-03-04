package myproject.shop.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Order {
    private int orderId;
    private int memberId;
    private List<OrderItem> itemList = new ArrayList<>();

    public Order(int memberId, OrderItem... items) {
        this.memberId = memberId;
        for (OrderItem orderItem : items) {
            this.itemList.add(orderItem);
        }
    }
}
