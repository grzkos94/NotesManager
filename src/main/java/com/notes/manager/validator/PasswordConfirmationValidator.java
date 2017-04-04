package com.notes.manager.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.notes.manager.domain.User;

public class PasswordConfirmationValidator implements Validator {
	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		if (!user.getPassword().equals(user.getPasswordConfirm())) {
			errors.rejectValue("passwordConfirm", "", "This field must be equal to password");
		}
	}
}
