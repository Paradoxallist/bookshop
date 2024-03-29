package store.bookshop.service.user;

import store.bookshop.dto.user.CreateUserRequestDto;
import store.bookshop.dto.user.UserDto;
import store.bookshop.exception.RegistrationException;

public interface UserService {
    UserDto register(CreateUserRequestDto requestDto) throws RegistrationException;
}
