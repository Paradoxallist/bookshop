package store.bookshop.service.category;

import java.util.List;
import store.bookshop.dto.book.BookDto;
import store.bookshop.dto.category.CategoryDto;
import store.bookshop.dto.category.CreateCategoryRequestDto;

public interface CategoryService {
    List<CategoryDto> findAll();

    CategoryDto getById(Long id);

    List<BookDto> getBooksByCategoryId(Long id);

    CategoryDto save(CreateCategoryRequestDto categoryDto);

    CategoryDto update(Long id, CreateCategoryRequestDto categoryDto);

    void deleteById(Long id);
}
