package store.bookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.bookshop.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
