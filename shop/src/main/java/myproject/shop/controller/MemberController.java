package myproject.shop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import myproject.shop.domain.Member;
import myproject.shop.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members")
    public List<Member> members() throws SQLException {
        return memberService.findAll();
    }

    @GetMapping("/members/{memberId}")
    public Member member(@PathVariable("memberId") int memberId) throws SQLException {
        return memberService.findOne(memberId);
    }
    
    @PostMapping("/members/new")
    public Member joinMember(@RequestBody @Valid Member member) throws SQLException {
        return memberService.join(member);
    }
}
