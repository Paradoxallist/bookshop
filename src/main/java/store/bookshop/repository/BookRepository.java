package store.bookshop.repository;

import java.util.List;
import store.bookshop.model.Book;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();
}
