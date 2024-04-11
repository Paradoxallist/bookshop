package store.bookshop.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import store.bookshop.model.OrderItem;

public interface OrderItemRepository  extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(Long orderId);
}
