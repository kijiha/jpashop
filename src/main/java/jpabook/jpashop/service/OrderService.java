package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /*
        주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        // 오직 스테틱 메서드로만 객체생성을 강제해야할 때
        // 객체의 생성자를 protected 로 선언 (JPA 권장스펙)
        // 롬복  @NoArgsConstructor(access = AccessLevel.PROTECTED)


        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);
        /*
        * 본래라면 Order가 persist 되면  데이터 필드 delivery 와 orderItems도 persist 해줘야하지만
        * Cascade All 옵션을 사용함으로서 데이터필드도 연달아서 영속화 된다 .
        * 라이프사이클에 대해서 동일한 객체에 대해서만, 다른곳이 참조할 수 없는(private) 엔티티인 경우  cascade
        * 다른곳에서 참조하는경우 위험함
        * 개념이 명확하게 알지못하면 쓰지 않는것을 권장
        *  */

        // 주문 저장
        orderRepository.save(order);


        return order.getId();
    }


    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long id) {
        //주문 엔티티 조회
        Order order = orderRepository.findOne(id);
        // 주문 취소
        order.cancel();
        /*
        sql을 직접 다루는 방식과 달리 jpa는 데이터 변경을 감지하면 자동으로 업데이트 쿼리를 날린다
         */

    }

/*검색*/
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch);
    }

}
