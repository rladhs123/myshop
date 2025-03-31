package myproject.shop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import myproject.shop.domain.Order;
import myproject.shop.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/orders")
    public List<Order> orders() throws SQLException {
        return orderService.findAllOrder();
    }

    @GetMapping("/orders/{memberId}")
    public List<Order> getOrder(@PathVariable("memberId") int memberId) throws SQLException {
        return orderService.findOrder(memberId);
    }

    @PostMapping("/orders/new")
    public Order createOrder(@RequestBody @Valid Order order) throws SQLException {
        return orderService.createOrder(order);
    }
}
