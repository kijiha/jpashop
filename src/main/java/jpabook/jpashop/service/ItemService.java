package jpabook.jpashop.service;

import jpabook.jpashop.domain.controller.ItemDto;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);

    }

    @Transactional
    public void updateItem(ItemDto itemDto) {
        Item item = itemRepository.findOne(itemDto.getId());
        // 준영속 엔티티를 트랜잭션이 있는 서비스 계층에서 엔티티 매니저로 조회함으로서 영속화
        item.attributeChange(
                itemDto.getName()
                ,itemDto.getPrice()
                , itemDto.getPrice());
        // setter 사용 지양하기위한 변경 메서드 - 변경 추적 용이
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long id) {
        return itemRepository.findOne(id);
    }
}
