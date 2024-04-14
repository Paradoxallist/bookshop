package store.bookshop.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import store.bookshop.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
