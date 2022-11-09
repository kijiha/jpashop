package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional //테스트 케이스에 붙이면 테스트 끝나고 롤백된다.
public class MemberServiceTest {


    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long joinId = memberService.join(member);

        //then
        em.flush();
        assertEquals(member,memberRepository.findOne(joinId));


    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1= new Member();
        member1.setName("k1");
        Member member2 = new Member();
        member2.setName("k1");

        //when
        memberService.join(member1);
        memberService.join(member2); // 예외가 터져야한다

        /* 제어흐름을 따라 날아오는 Exception을 catch를 해야한다. */

        //then
        fail("예외가 발생해야한다");
    }

}