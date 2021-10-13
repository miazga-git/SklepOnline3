package wat.ecommerce.SklepOnline.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Basket {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    private List<Item> items;
    private String user;
    private int isPaid = 0;
    private boolean isDeleted = false;
    private String state = "";
    private String city = "";
    private String street = "";
    private String zip = "";
    private String nameAndSurname= "";



    public Basket(List<Item> items, String user, int isPaid, boolean isDeleted, String state, String city, String street, String zip, String nameAndSurname) {
        this.items = items;
        this.user = user;
        this.isPaid = isPaid;
        this.isDeleted = isDeleted;
        this.state = state;
        this.city = city;
        this.street = street;
        this.zip = zip;
        this.nameAndSurname = nameAndSurname;
    }
    public Basket(){

    }

}
