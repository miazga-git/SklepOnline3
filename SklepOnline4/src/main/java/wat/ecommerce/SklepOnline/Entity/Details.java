package wat.ecommerce.SklepOnline.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Details {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade=CascadeType.ALL)//koszyk ma wiele detali / detal ma 1 koszyk
    private Basket basket;

    @ManyToOne//item ma wiele detali / detal ma 1 item
    private Item item;

    private int quantity;

    public Details() {

    }
    public Details(Basket basket, Item item, int quantity) {
        this.basket = basket;
        this.item = item;
        this.quantity = quantity;
    }
}
