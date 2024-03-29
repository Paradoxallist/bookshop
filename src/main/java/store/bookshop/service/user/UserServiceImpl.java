package store.bookshop.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import store.bookshop.dto.user.CreateUserRequestDto;
import store.bookshop.dto.user.UserDto;
import store.bookshop.exception.RegistrationException;
import store.bookshop.mapper.UserMapper;
import store.bookshop.model.User;
import store.bookshop.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto register(CreateUserRequestDto requestDto) throws RegistrationException {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException("Can't register user");
        }
        User user = new User();
        user.setEmail(requestDto.getEmail());
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setShippingAddress(requestDto.getShippingAddress());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }
}
