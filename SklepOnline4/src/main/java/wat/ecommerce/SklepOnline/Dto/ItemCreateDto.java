package wat.ecommerce.SklepOnline.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ItemCreateDto {

    private Double price;
    private String name;
    private String description;
    private int quantity;
    private String url;

    public ItemCreateDto(Double price, String name, String description, int quantity, String url) {
        this.price = price;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.url = url;
    }
}
