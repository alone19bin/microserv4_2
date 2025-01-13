package individuals.api.validation;

import individuals.common.dto.UserDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UserDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDto userDto = (UserDto) target;

        // // Проверка email
        if (userDto.getEmail() == null || userDto.getEmail().isEmpty()) {
            errors.rejectValue("email", "email.required", "Email обязателен");
        } else if (!isValidEmail(userDto.getEmail())) {
            errors.rejectValue("email", "email.invalid", "Некорректный формат email");
        }

        // // Проверка имени
        if (userDto.getFirstName() == null || userDto.getFirstName().isEmpty()) {
            errors.rejectValue("firstName", "firstName.required", "Имя обязательно");
        }

        // // Проверка фамилии
        if (userDto.getLastName() == null || userDto.getLastName().isEmpty()) {
            errors.rejectValue("lastName", "lastName.required", "Фамилия обязательна");
        }
    }

    // // Простая валидация email
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }
}