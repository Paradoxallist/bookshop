package store.bookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.bookshop.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
