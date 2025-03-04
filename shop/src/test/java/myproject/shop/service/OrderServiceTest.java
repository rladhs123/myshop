package myproject.shop.service;

import myproject.shop.domain.Item;
import myproject.shop.domain.Member;
import myproject.shop.domain.Order;
import myproject.shop.domain.OrderItem;
import myproject.shop.repository.ItemRepository;
import myproject.shop.repository.MemberRepository;
import myproject.shop.repository.OrderItemRepository;
import myproject.shop.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    MemberRepository memberRepository = new MemberRepository();
    MemberService memberService = new MemberService(memberRepository);
    ItemRepository itemRepository = new ItemRepository();
    OrderItemRepository orderItemRepository = new OrderItemRepository();
    OrderRepository orderRepository = new OrderRepository();
    OrderService orderService = new OrderService(orderRepository, orderItemRepository, itemRepository);

    @Test
    void createOrder() throws SQLException {
        Member member = new Member(123123, "member", 2);
        memberService.join(member);
        Item item1 = new Item(100, 5000);
        itemRepository.save(item1);
        Item item2 = new Item(100, 10000);
        itemRepository.save(item2);
        OrderItem orderItem1 = new OrderItem(item1.getItemId(), 2);
        OrderItem orderItem2 = new OrderItem(item2.getItemId(), 4);

        Order order = new Order(member.getMemberId(), orderItem1, orderItem2);
        orderService.createOrder(order);

        assertThat(order.getOrderId()).isEqualTo(orderItem1.getOrderId());
        assertThat(order.getOrderId()).isEqualTo(orderItem2.getOrderId());

    }

    @Test
    void findOrder() {
    }
}