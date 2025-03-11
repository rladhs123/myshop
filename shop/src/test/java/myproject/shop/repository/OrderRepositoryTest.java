package myproject.shop.repository;

import myproject.shop.domain.Item;
import myproject.shop.domain.Member;
import myproject.shop.domain.Order;
import myproject.shop.domain.OrderItem;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;

class OrderRepositoryTest {


    MemberRepository memberRepository;
    ItemRepository itemRepository;
    OrderItemRepository orderItemRepository;
    OrderRepository orderRepository;

    @BeforeEach
    void before() {
    }

    @Test
    void create() throws SQLException {
        Member member = new Member(2222, "member", 123);
        memberRepository.save(member);
        Item item = new Item(100, 10000);
        itemRepository.save(item);
        Order order = new Order(member.getMemberId(), new OrderItem(item.getItemId(), 10));

        orderRepository.create(order);

        Assertions.assertThat(order.getMemberId()).isEqualTo(member.getMemberId());

    }

    @Test
    void findOrder() throws SQLException {
        Order order = orderRepository.findOrder(111);

        assertThat(order.getOrderId()).isEqualTo(5);
    }
}