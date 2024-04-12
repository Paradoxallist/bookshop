package store.bookshop.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import store.bookshop.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("SELECT oi "
            + "FROM OrderItem oi "
            + "JOIN oi.order o "
            + "WHERE o.user.id = :userId AND o.id = :orderId AND oi.id = :itemId")
    Optional<OrderItem> findByUserIdAndOrderIdAndItemId(@Param("userId") Long userId,
                                                        @Param("orderId") Long orderId,
                                                        @Param("itemId") Long itemId);

    @Query("SELECT oi "
            + "FROM OrderItem oi "
            + "JOIN oi.order o "
            + "WHERE o.user.id = :userId AND o.id = :orderId")
    List<OrderItem> findByUserIdAndOrderId(@Param("userId") Long userId,
                                           @Param("orderId") Long orderId);
}
