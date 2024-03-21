package store.bookshop.service;

import java.util.List;
import store.bookshop.dto.BookDto;
import store.bookshop.dto.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);
}
