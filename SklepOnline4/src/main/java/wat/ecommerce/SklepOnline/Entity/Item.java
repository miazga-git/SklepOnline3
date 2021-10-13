package wat.ecommerce.SklepOnline.Entity;

import lombok.Data;
import wat.ecommerce.SklepOnline.Dto.ItemCreateDto;
import wat.ecommerce.SklepOnline.Dto.ItemDto;

import javax.persistence.*;

@Data
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double price;
    private String name;
    private String description;
    private int quantity;
    private String url;

    public Item(){

    }
    public Item(String name, String description, Double price, String url, int quantity) {
        this.price = price;
        this.name = name;
        this.description = description;
        this.url = url;
        this.quantity = quantity;
    }
    public Item(Long id, String name, String description, Double price, String url, int quantity) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.description = description;
        this.url = url;
        this.quantity = quantity;
    }
    public Item(ItemCreateDto itemCreateDto){
        this.price = itemCreateDto.getPrice();
        this.name = itemCreateDto.getName();
        this.description = itemCreateDto.getDescription();
        this.url = itemCreateDto.getUrl();
        this.quantity = itemCreateDto.getQuantity();
    }

}