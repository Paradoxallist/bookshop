package store.bookshop.mapper;

import org.mapstruct.Mapper;
import store.bookshop.config.MapperConfig;
import store.bookshop.dto.user.CreateUserRequestDto;
import store.bookshop.dto.user.UserDto;
import store.bookshop.model.User;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserDto toDto(User user);

    User toModel(CreateUserRequestDto user);
}
