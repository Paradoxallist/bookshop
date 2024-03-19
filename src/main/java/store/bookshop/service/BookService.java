package store.bookshop.service;

import java.util.List;
import store.bookshop.model.Book;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
