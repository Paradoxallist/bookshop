package store.bookshop.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import store.bookshop.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b JOIN b.categories c WHERE c.id = :id")
    List<Book> findAllByCategoryId(@Param("id") Long id);
}
