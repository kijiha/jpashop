package jpabook.jpashop.domain.controller;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ItemDto {
    public Long id;
    private String name;
    private int price;
    private int stockQuantity;

}
