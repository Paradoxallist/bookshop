package store.bookshop.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.bookshop.dto.user.CreateUserRequestDto;
import store.bookshop.dto.user.UserDto;
import store.bookshop.exeption.RegistrationException;
import store.bookshop.service.user.UserService;

@Tag(name = "User manager", description = "Endpoint for managing users")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {
    private final UserService userService;

    @PostMapping("/registration")
    public UserDto registerUser(@RequestBody @Valid CreateUserRequestDto requestDto)
            throws RegistrationException {
        return userService.register(requestDto);
    }
}
