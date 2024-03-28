package store.bookshop.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.SneakyThrows;
import store.bookshop.dto.user.CreateUserRequestDto;
import store.bookshop.exeption.RegistrationException;

public class PasswordMatchValidator implements
        ConstraintValidator<PasswordMatch, CreateUserRequestDto> {

    @SneakyThrows
    @Override
    public boolean isValid(CreateUserRequestDto createUserRequestDto,
                           ConstraintValidatorContext constraintValidatorContext) {
        String plainPassword = createUserRequestDto.getPassword();
        String repeatPassword = createUserRequestDto.getRepeatPassword();
        if (!plainPassword.equals(repeatPassword)) {
            throw new RegistrationException("The password fields must match");
        }
        return true;
    }
}
