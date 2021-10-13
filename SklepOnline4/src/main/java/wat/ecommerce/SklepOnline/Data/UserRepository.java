package wat.ecommerce.SklepOnline.Data;

import org.springframework.data.jpa.repository.JpaRepository;
import wat.ecommerce.SklepOnline.Entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username); //dodana metoda znajdowania użytkownika po nazwie użytkownika , wykorzystywana w klasie UserRepositoryUserDetailsService
}
