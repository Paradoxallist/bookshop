package store.bookshop.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.bookshop.dto.BookDto;
import store.bookshop.dto.CreateBookRequestDto;
import store.bookshop.exeption.EntityNotFoundException;
import store.bookshop.mapper.BookMapper;
import store.bookshop.model.Book;
import store.bookshop.repository.BookRepository;
import store.bookshop.service.BookService;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        return bookMapper.toDo(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDo)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id: " + id));
        return bookMapper.toDo(book);
    }
}
