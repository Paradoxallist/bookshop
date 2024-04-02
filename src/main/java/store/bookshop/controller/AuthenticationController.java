package store.bookshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.bookshop.dto.user.CreateUserRequestDto;
import store.bookshop.dto.user.UserDto;
import store.bookshop.dto.user.UserLoginRequestDto;
import store.bookshop.exception.RegistrationException;
import store.bookshop.service.user.UserService;

@Tag(name = "User manager", description = "Endpoint for managing users")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;

    @PostMapping("/registration")
    @Operation(summary = "Registration", description = "Create a new User")
    public UserDto registerUser(@RequestBody @Valid CreateUserRequestDto requestDto)
            throws RegistrationException {
        return userService.register(requestDto);
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Login in account")
    public boolean login(@RequestBody UserLoginRequestDto requestDto) {
        return true;
    }
}
