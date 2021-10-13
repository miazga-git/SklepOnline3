package wat.ecommerce.SklepOnline.Data;

import org.springframework.data.jpa.repository.JpaRepository;
import wat.ecommerce.SklepOnline.Security.ConfirmationToken;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

}