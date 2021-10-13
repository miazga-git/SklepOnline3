package wat.ecommerce.SklepOnline.Rest;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wat.ecommerce.SklepOnline.Dto.ItemCreateDto;
import wat.ecommerce.SklepOnline.Dto.ItemDto;
import wat.ecommerce.SklepOnline.Service.ItemService;

import java.security.Principal;
import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/iteminfo")
public class ItemRestController {

    private final ItemService itemService;

    public ItemRestController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    ResponseEntity<List<ItemDto>> findAllItems(){
        return new ResponseEntity<>(itemService.findAllItems(),HttpStatus.OK);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDto> getItemById(@PathVariable Long itemId){
        return new ResponseEntity<>(itemService.getItemById(itemId),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ItemDto> createItem(@RequestBody ItemCreateDto itemCreateDto){
        return new ResponseEntity<>(itemService.createItem(itemCreateDto),HttpStatus.CREATED);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
