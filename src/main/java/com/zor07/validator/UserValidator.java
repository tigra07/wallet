package com.zor07.validator;

import com.zor07.domain.dto.UserDto;
import com.zor07.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDto user = (UserDto) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (user.getUserName().length() < 6 || user.getUserName().length() > 32) {
            errors.rejectValue("username", "User name length should be from 6 to 32 character!");
        }
        if (userService.loadUserByUsername(user.getUserName()) != null) {
            errors.rejectValue("username", "Username " + user.getUserName() + " already exists!");
        }

        if (userService.userWithEmailExists(user.getEmail())){
            errors.rejectValue("email", "User with " + user.getEmail() + " already exists!");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 5 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "password size should be from 5 to 32 character!");
        }

        if (!user.getMatchingPassword().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Passwords not equal!");
        }
    }

}
