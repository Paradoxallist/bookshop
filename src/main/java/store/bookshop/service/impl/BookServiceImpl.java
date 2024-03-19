package store.bookshop.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.bookshop.model.Book;
import store.bookshop.repository.BookRepository;
import store.bookshop.service.BookService;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}
