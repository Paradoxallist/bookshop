package store.bookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.bookshop.model.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    ShoppingCart findByUserId(Long id);
}
