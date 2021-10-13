package wat.ecommerce.SklepOnline.Data;

import org.springframework.data.jpa.repository.JpaRepository;
import wat.ecommerce.SklepOnline.Entity.Item;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item,Long> {
    Optional<Item> findByName(String name);
}
