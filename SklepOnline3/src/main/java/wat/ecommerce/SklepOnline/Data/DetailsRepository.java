package wat.ecommerce.SklepOnline.Data;

import org.springframework.data.jpa.repository.JpaRepository;
import wat.ecommerce.SklepOnline.Entity.Details;

public interface DetailsRepository extends JpaRepository<Details,Long> {}
