package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
// 싱글테이블 상속전략시의 테이블에서 자식 개체들을 구분하는 테이블
// movie = M , Book = B ...
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비즈니스 로직 ==//
    /* 외부 객체에서 재고 관리 로직을 수행 (setter)를 사용하는등) 해서 할 필요 없이
        데이터를 가지고 있는 객체가 내부에서 로직을 수행시켜 응집도를 높인다
    * */
    public void addStockQuantity(int stockQuantity) {
        this.stockQuantity +=stockQuantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }

        this.stockQuantity =restStock;

    }

    public void attributeChange(String name, int price, int stockQuantity) {
        this.name = name;
        this.price= price;
        this.stockQuantity= stockQuantity;
    }

}
