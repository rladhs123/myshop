package myproject.shop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import myproject.shop.domain.Member;
import myproject.shop.domain.Order;
import myproject.shop.repository.OrderRepository;
import myproject.shop.service.MemberService;
import myproject.shop.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @GetMapping("/members")
    public List<Member> members() throws SQLException {
        return memberService.findAll();
    }

    @GetMapping("/members/{memberId}")
    public Member member(@PathVariable("memberId") int memberId) throws SQLException {
        return memberService.findOne(memberId);
    }

    @GetMapping("/orders/{memberId}")
    public Order getOrder(@PathVariable("memberId") int memberId) throws SQLException {
        return orderService.findOrder(memberId);
    }

    @PostMapping("/orders/new")
    public Order createOrder(@RequestBody @Valid Order order) throws SQLException {
        return orderService.createOrder(order);
    }

}
