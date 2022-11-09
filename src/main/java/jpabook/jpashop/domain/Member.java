package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import jpabook.jpashop.domain.Order;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();
}
