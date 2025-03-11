package myproject.shop.service;

import com.zaxxer.hikari.HikariDataSource;
import myproject.shop.connection.ConnectionConst;
import myproject.shop.domain.Item;
import myproject.shop.domain.Member;
import myproject.shop.domain.Order;
import myproject.shop.domain.OrderItem;
import myproject.shop.repository.ItemRepository;
import myproject.shop.repository.MemberRepository;
import myproject.shop.repository.OrderItemRepository;
import myproject.shop.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.SQLException;

import static myproject.shop.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    MemberRepository memberRepository;
    MemberService memberService;
    ItemRepository itemRepository;
    OrderItemRepository orderItemRepository;
    OrderRepository orderRepository;
    OrderService orderService;

    @BeforeEach
    void before() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        memberRepository = new MemberRepository(dataSource);
        memberService = new MemberService(memberRepository);


    }

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