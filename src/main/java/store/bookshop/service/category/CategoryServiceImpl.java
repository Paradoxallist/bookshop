package store.bookshop.service.category;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.bookshop.dto.book.BookDto;
import store.bookshop.dto.category.CategoryDto;
import store.bookshop.dto.category.CreateCategoryRequestDto;
import store.bookshop.exception.EntityNotFoundException;
import store.bookshop.mapper.BookMapper;
import store.bookshop.mapper.CategoryMapper;
import store.bookshop.model.Category;
import store.bookshop.repository.BookRepository;
import store.bookshop.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id: " + id));
        return categoryMapper.toDto(category);
    }

    @Override
    public List<BookDto> getBooksByCategoryId(Long id) {
        return bookRepository.findByCategories_Id(id).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public CategoryDto save(CreateCategoryRequestDto requestDto) {
        Category category = categoryMapper.toModel(requestDto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto update(Long id, CreateCategoryRequestDto requestDto) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Book doesn't exist with id: " + id);
        }
        Category category = categoryMapper.toModel(requestDto);
        category.setId(id);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
