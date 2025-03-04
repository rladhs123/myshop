package myproject.shop.service;

import lombok.RequiredArgsConstructor;
import myproject.shop.domain.Item;
import myproject.shop.domain.Member;
import myproject.shop.domain.Order;
import myproject.shop.domain.OrderItem;
import myproject.shop.repository.ItemRepository;
import myproject.shop.repository.OrderItemRepository;
import myproject.shop.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;

    public Order createOrder(Order order) throws SQLException {
        Order createOrder = orderRepository.create(order);

        for (OrderItem orderItem : createOrder.getItemList()) {
            orderItem.setOrderId(createOrder.getOrderId());
            Item findItem = itemRepository.findById(orderItem.getItemId());
            orderItem.setOrderPrice(orderItem.getQuantity() * findItem.getPrice());
            orderItemRepository.createOrderItem(orderItem);
            findItem.setStockQuantity(findItem.getStockQuantity() - orderItem.getQuantity());
            itemRepository.save(findItem);
        }

        return createOrder;
    }

    public Order findOrder(int memberId) throws SQLException {
        return orderRepository.findOrder(memberId);
    }
}
