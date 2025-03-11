package myproject.shop.repository;

import myproject.shop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
class MemberRepositoryTest {

    MemberRepository memberRepository;

    @BeforeEach
    void before() throws SQLException {
        memberRepository.deleteAll();
    }

    @AfterEach
    void after() throws SQLException {
        memberRepository.deleteAll();
    }

    @Test
    void crud() throws SQLException {

        Member member1 = new Member(111, "memberA", 1);
        Member member2= new Member(222, "memberB", 2);
        Member member3 = new Member(333, "memberC", 3);

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        Member findMember = memberRepository.findById(member1.getMemberId());

        assertThat(member1).isEqualTo(findMember);

        //findAll
        List<Member> memberList = memberRepository.findAll();

        assertThat(memberList).contains(member1, member2, member3);
    }
}