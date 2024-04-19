package store.bookshop.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import store.bookshop.dto.book.BookDto;
import store.bookshop.dto.book.CreateBookRequestDto;
import store.bookshop.exception.EntityNotFoundException;
import store.bookshop.mapper.BookMapper;
import store.bookshop.model.Book;
import store.bookshop.model.Category;
import store.bookshop.repository.BookRepository;
import store.bookshop.service.book.BookServiceImpl;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {
    private static final Long ID = 1L;
    private Book book;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        book = new Book(ID);
        book.setTitle("Dark world");
        book.setAuthor("Jhon Fordon");
        book.setPrice(BigDecimal.valueOf(500));
        book.setIsbn("12345678904683");
        book.setCategories(Set.of(new Category(ID)));
    }

    @Test
    @DisplayName("Save book with valid data")
    void saveBook_ValidData_Success() {
        CreateBookRequestDto requestDto = createBookRequestDto();

        BookDto expected = createBookDto();

        Mockito.when(bookMapper.toModel(requestDto)).thenReturn(book);
        Mockito.when(bookMapper.toDto(book)).thenReturn(expected);
        Mockito.when(bookRepository.save(book)).thenReturn(book);

        BookDto actual = bookService.save(requestDto);

        Assertions.assertEquals(expected, actual);
        Mockito.verify(bookRepository, Mockito.times(1)).save(book);
    }

    @Test
    @DisplayName("Find all books in database")
    void findAllBooks_ValidPageable_Success() {
        List<Book> books = new ArrayList<>();
        books.add(book);

        List<BookDto> expected = new ArrayList<>();
        expected.add(createBookDto());

        Page<Book> bookPage = new PageImpl<>(books);

        Mockito.when(bookRepository.findAll(Mockito.any(Pageable.class))).thenReturn(bookPage);
        Mockito.when(bookMapper.toDto(book)).thenReturn(createBookDto());

        List<BookDto> actual = bookService.findAll(Mockito.mock(Pageable.class));
        Assertions.assertEquals(expected, actual);
        Mockito.verify(bookRepository, Mockito.times(1))
                .findAll(Mockito.any(Pageable.class));
    }

    @Test
    @DisplayName("Check for an exception if the book id is invalid")
    void findBookById_InvalidId_Failed() {
        Mockito.when(bookRepository.findById(ID)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> bookService.findById(ID));
        Mockito.verify(bookRepository, Mockito.times(1)).findById(ID);
    }

    @Test
    @DisplayName("Find book by valid id")
    void findBookById_ValidId_Success() {
        Mockito.when(bookRepository.findById(ID)).thenReturn(Optional.of(book));
        BookDto expected = createBookDto();
        Mockito.when(bookMapper.toDto(book)).thenReturn(expected);
        BookDto actual = bookService.findById(ID);
        Assertions.assertEquals(expected, actual);
        Mockito.verify(bookRepository, Mockito.times(1)).findById(ID);
    }

    @Test
    @DisplayName("Update book with valid id and data")
    void updateBook_ValidIdAndData_Success() {
        Book newBook = book;
        newBook.setTitle("White world");
        newBook.setPrice(BigDecimal.valueOf(369));

        CreateBookRequestDto requestDto = createBookRequestDto();
        requestDto.setTitle("White world");
        requestDto.setPrice(BigDecimal.valueOf(369));

        BookDto expected = createBookDto();
        expected.setTitle(newBook.getTitle());
        expected.setPrice(newBook.getPrice());

        Mockito.when(bookRepository.existsById(ID)).thenReturn(true);
        Mockito.when(bookMapper.toModel(requestDto)).thenReturn(book);
        Mockito.when(bookMapper.toDto(newBook)).thenReturn(expected);
        Mockito.when(bookRepository.save(newBook)).thenReturn(newBook);

        BookDto actual = bookService.updateById(requestDto, ID);

        Assertions.assertEquals(expected.getTitle(), actual.getTitle());
        Assertions.assertEquals(expected.getPrice(), actual.getPrice());
        Assertions.assertEquals(expected, actual);
        Mockito.verify(bookRepository, Mockito.times(1)).existsById(ID);
        Mockito.verify(bookRepository, Mockito.times(1)).save(newBook);
    }

    private CreateBookRequestDto createBookRequestDto() {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle("Dark world");
        requestDto.setAuthor("Jhon Fordon");
        requestDto.setPrice(BigDecimal.valueOf(500));
        requestDto.setIsbn("12345678904683");
        return requestDto;
    }

    private BookDto createBookDto() {
        BookDto bookDto = new BookDto();
        bookDto.setId(ID);
        bookDto.setTitle("Dark world");
        bookDto.setAuthor("Jhon Fordon");
        bookDto.setPrice(BigDecimal.valueOf(500));
        bookDto.setIsbn("12345678904683");
        return bookDto;
    }
}

