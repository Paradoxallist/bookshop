package store.bookshop.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import store.bookshop.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
