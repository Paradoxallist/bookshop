package store.bookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.bookshop.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
