package store.bookshop.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import store.bookshop.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByCategories_Id(Long id);
}
