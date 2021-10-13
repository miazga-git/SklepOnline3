package wat.ecommerce.SklepOnline.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wat.ecommerce.SklepOnline.Entity.Basket;
import wat.ecommerce.SklepOnline.Entity.Details;
import wat.ecommerce.SklepOnline.Entity.Item;

import java.util.Optional;

@Setter
@Getter
@NoArgsConstructor
public class DetailsDto {

    private Long id;
    private BasketDto basket;
    private ItemDto item;
    private int quantity;

    public DetailsDto(Details details) {
        this.id = details.getId();
        this.basket = mapBasket(details.getBasket());
        this.item = mapItem(details.getItem());
        this.quantity = details.getQuantity();
    }

    private ItemDto mapItem(Item item) {
        return Optional.ofNullable(item)
                .map(ItemDto::new)
                .orElse(null);
    }
    private BasketDto mapBasket(Basket basket) {
        return Optional.ofNullable(basket)
                .map(BasketDto::new)
                .orElse(null);
    }
}
