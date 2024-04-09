package store.bookshop.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import store.bookshop.config.MapperConfig;
import store.bookshop.dto.book.BookDto;
import store.bookshop.dto.book.CreateBookRequestDto;
import store.bookshop.model.Book;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    List<BookDto> toDtoList(List<Book> books);

    Book toModel(CreateBookRequestDto requestDto);
}
