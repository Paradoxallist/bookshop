package store.bookshop.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import store.bookshop.dto.user.CreateUserRequestDto;

public class PasswordMatchValidator implements
        ConstraintValidator<PasswordMatch, CreateUserRequestDto> {

    @Override
    public boolean isValid(CreateUserRequestDto createUserRequestDto,
                           ConstraintValidatorContext constraintValidatorContext) {
        String plainPassword = createUserRequestDto.getPassword();
        String repeatPassword = createUserRequestDto.getRepeatPassword();
        return plainPassword.equals(repeatPassword);
    }
}
