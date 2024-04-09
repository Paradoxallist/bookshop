package store.bookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import store.bookshop.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE CartItem ci SET ci.isDeleted = true WHERE ci.id = :id")
    void deleteByIdCustom(Long id);
}
