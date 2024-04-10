package store.bookshop.service.user;

import jakarta.transaction.Transactional;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import store.bookshop.dto.user.CreateUserRequestDto;
import store.bookshop.dto.user.UserDto;
import store.bookshop.exception.RegistrationException;
import store.bookshop.mapper.UserMapper;
import store.bookshop.model.Role;
import store.bookshop.model.User;
import store.bookshop.repository.RoleRepository;
import store.bookshop.repository.UserRepository;
import store.bookshop.service.shoppingcart.ShoppingCartService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    private final ShoppingCartService shoppingCartService;

    @Override
    @Transactional
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
        user.setRoles(Collections.singleton(roleRepository.findByRole(Role.RoleName.ROLE_USER)));

        User savedUser = userRepository.save(user);
        shoppingCartService.createShoppingCart(savedUser);

        return userMapper.toDto(savedUser);
    }

    public UserDto findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Can't find user by email: " + email));
    }
}
