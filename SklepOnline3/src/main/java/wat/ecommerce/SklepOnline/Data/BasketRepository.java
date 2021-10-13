package wat.ecommerce.SklepOnline.Data;

import org.springframework.data.jpa.repository.JpaRepository;
import wat.ecommerce.SklepOnline.Entity.Basket;

public interface BasketRepository extends JpaRepository<Basket,Long> {
}
