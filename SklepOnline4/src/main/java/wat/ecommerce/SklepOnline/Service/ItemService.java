package wat.ecommerce.SklepOnline.Service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wat.ecommerce.SklepOnline.Data.ItemRepository;
import wat.ecommerce.SklepOnline.Dto.ItemCreateDto;
import wat.ecommerce.SklepOnline.Dto.ItemDto;
import wat.ecommerce.SklepOnline.Entity.Item;
import wat.ecommerce.SklepOnline.exception.ItemAlreadyExistsException;
import wat.ecommerce.SklepOnline.exception.ItemNotFoundException;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<ItemDto> findAllItems() {
        return itemRepository.findAll().stream().map(ItemDto::new).collect(Collectors.toList());
    }

    public ItemDto getItemById(Long itemId){
        return itemRepository.findById(itemId).map(item -> new ItemDto(item)).orElseThrow(() -> new ItemNotFoundException(itemId));

    }

    public ItemDto createItem(ItemCreateDto itemCreateDto){
        if(itemExists(itemCreateDto.getName()))
            throw new ItemAlreadyExistsException(itemCreateDto.getName());
        Item item = new Item(itemCreateDto);
        return new ItemDto(itemRepository.save(item));

    }

    private boolean itemExists(String name) {
        return itemRepository.findByName(name).isPresent();
    }

    public void deleteItem(Long itemId) {
        itemRepository.delete(itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId)));
    }

}
