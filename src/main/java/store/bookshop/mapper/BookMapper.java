package store.bookshop.mapper;

import org.mapstruct.Mapper;
import store.bookshop.config.MapperConfig;
import store.bookshop.dto.BookDto;
import store.bookshop.dto.CreateBookRequestDto;
import store.bookshop.model.Book;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);
}
