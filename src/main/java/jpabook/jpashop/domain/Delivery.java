package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {
    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery",fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    // TYPE.ORDINAL은 숫자 타입  숫자타입의 ENUM을 설정하면 나중에 추가되면 숫자 인덱스가 바뀌어서 좆된다.
    private DeliveryStatus status; // READY COMP
}
