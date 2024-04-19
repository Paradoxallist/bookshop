package store.bookshop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import store.bookshop.dto.category.CategoryDto;
import store.bookshop.dto.category.CreateCategoryRequestDto;
import store.bookshop.exception.EntityNotFoundException;
import store.bookshop.mapper.CategoryMapper;
import store.bookshop.model.Category;
import store.bookshop.repository.CategoryRepository;
import store.bookshop.service.category.CategoryServiceImpl;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    private static final Long ID = 1L;
    private Category category;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        category = new Category(ID);
        category.setName("Fantasy");
        category.setDescription("Adventures of Magic and Sword");
    }

    @Test
    @DisplayName("Save category with valid data")
    void saveCategory_ValidData_Success() {
        CreateCategoryRequestDto requestDto = createCategoryRequestDto();

        CategoryDto expected = createCategoryDto();

        Mockito.when(categoryMapper.toModel(requestDto)).thenReturn(category);
        Mockito.when(categoryMapper.toDto(category)).thenReturn(expected);
        Mockito.when(categoryRepository.save(category)).thenReturn(category);

        CategoryDto actual = categoryService.save(requestDto);

        Assertions.assertEquals(expected, actual);
        Mockito.verify(categoryRepository, Mockito.times(1)).save(category);
    }

    @Test
    @DisplayName("Find all categories in database")
    void findAllCategories_ValidPageable_Success() {
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        CategoryDto categoryDto = createCategoryDto();
        List<CategoryDto> expected = new ArrayList<>();
        expected.add(categoryDto);

        Mockito.when(categoryRepository.findAll()).thenReturn(categories);
        Mockito.when(categoryMapper.toDtoList(categories)).thenReturn(List.of(createCategoryDto()));

        List<CategoryDto> actual = categoryService.findAll();
        Assertions.assertEquals(expected, actual);
        Mockito.verify(categoryRepository,
                Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Check for an exception if the category id is invalid")
    void findCategoryById_InvalidId_Failed() {
        Mockito.when(categoryRepository.findById(ID)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> categoryService.getById(ID));
        Mockito.verify(categoryRepository, Mockito.times(1)).findById(ID);
    }

    @Test
    @DisplayName("Find category by valid id")
    void findCategoryById_ValidId_Success() {
        Mockito.when(categoryRepository.findById(ID)).thenReturn(Optional.of(category));
        CategoryDto expected = createCategoryDto();
        Mockito.when(categoryMapper.toDto(category)).thenReturn(expected);
        CategoryDto actual = categoryService.getById(ID);
        Assertions.assertEquals(expected, actual);
        Mockito.verify(categoryRepository, Mockito.times(1)).findById(ID);
    }

    @Test
    @DisplayName("Update category with valid id and data")
    void updateCategory_ValidIdAndData_Success() {
        Category newCategory = category;
        newCategory.setDescription("Adventures of Magic");

        CategoryDto expected = createCategoryDto();
        expected.setDescription(newCategory.getDescription());

        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto();
        requestDto.setName("Fantasy");
        requestDto.setDescription("Adventures of Magic");

        Mockito.when(categoryRepository.existsById(ID)).thenReturn(true);
        Mockito.when(categoryMapper.toModel(requestDto)).thenReturn(newCategory);
        Mockito.when(categoryMapper.toDto(newCategory)).thenReturn(expected);
        Mockito.when(categoryRepository.save(newCategory)).thenReturn(newCategory);

        CategoryDto actual = categoryService.updateById(ID, requestDto);

        Assertions.assertEquals(expected.getDescription(), actual.getDescription());
        Assertions.assertEquals(expected, actual);

        Mockito.verify(categoryRepository, Mockito.times(1)).existsById(ID);
        Mockito.verify(categoryRepository, Mockito.times(1)).save(newCategory);
    }

    private CategoryDto createCategoryDto() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(ID);
        categoryDto.setName("Fantasy");
        categoryDto.setDescription("Adventures of Magic and Sword");
        return categoryDto;
    }

    private CreateCategoryRequestDto createCategoryRequestDto() {
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto();
        requestDto.setName("Fantasy");
        requestDto.setDescription("Adventures of Magic and Sword");
        return requestDto;
    }
}
