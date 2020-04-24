package ru.undframe.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.undframe.mode.User;
import ru.undframe.services.UserService;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User)target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "test", "error a ");
        if(user.getUsername().length()<8){
                errors.rejectValue("username","testb","test c");
        }


    }
}
