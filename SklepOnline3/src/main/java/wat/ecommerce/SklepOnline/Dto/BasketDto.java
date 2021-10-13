package wat.ecommerce.SklepOnline.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wat.ecommerce.SklepOnline.Entity.Basket;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
public class BasketDto {

    private Long id;
    private List<ItemDto> items;
    private String user;
    private int isPaid;
    private boolean isDeleted;
    private String state;
    private String city;
    private String street;
    private String zip;
    private String nameAndSurname;

    public BasketDto(Basket basket){
        this.id = basket.getId();
        this.items = mapItems(basket);
        this.user = basket.getUser();
        this.isPaid = basket.getIsPaid();
        this.isDeleted = basket.isDeleted();
        this.state = basket.getState();
        this.city = basket.getCity();
        this.street = basket.getStreet();
        this.zip = basket.getZip();
        this.nameAndSurname = basket.getNameAndSurname();

    }

    private List<ItemDto> mapItems(Basket basket){
        return Optional.ofNullable(basket.getItems()).orElseGet(Collections::emptyList).stream().map(ItemDto::new).collect(Collectors.toList());
    }
}
