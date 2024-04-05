package store.bookshop.mapper;

import org.mapstruct.Mapper;
import store.bookshop.config.MapperConfig;
import store.bookshop.dto.category.CategoryDto;
import store.bookshop.dto.category.CreateCategoryRequestDto;
import store.bookshop.model.Category;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toModel(CreateCategoryRequestDto requestDto);
}
