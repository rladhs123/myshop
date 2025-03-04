package myproject.shop.service;

import lombok.RequiredArgsConstructor;
import myproject.shop.domain.Member;
import myproject.shop.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public int join(Member member) throws SQLException {
        Member savedMember = memberRepository.save(member);
        return savedMember.getMemberId();
    }

    public Member findOne(int memberId) throws SQLException {
        return memberRepository.findById(memberId);
    }

    public List<Member> findAll() throws SQLException {
        List<Member> memberList = memberRepository.findAll();
        return memberList;
    }

    public void deleteAll() throws SQLException {
        memberRepository.deleteAll();
    }
}
