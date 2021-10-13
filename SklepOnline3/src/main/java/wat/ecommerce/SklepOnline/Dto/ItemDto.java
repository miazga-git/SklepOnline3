package wat.ecommerce.SklepOnline.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wat.ecommerce.SklepOnline.Entity.Item;

@Setter
@Getter
@NoArgsConstructor
public class ItemDto {

    private Long id;
    private Double price;
    private String name;
    private String description;
    private int quantity;
    private String url;

    public ItemDto(Item item) {
        this.id = item.getId();
        this.price = item.getPrice();
        this.name = item.getName();
        this.description = item.getDescription();
        this.quantity = item.getQuantity();
        this.url = item.getUrl();
    }
}

