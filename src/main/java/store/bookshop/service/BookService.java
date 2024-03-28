package store.bookshop.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import store.bookshop.dto.BookDto;
import store.bookshop.dto.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    BookDto updateById(CreateBookRequestDto requestDto, Long id);

    void deleteById(Long id);
}
