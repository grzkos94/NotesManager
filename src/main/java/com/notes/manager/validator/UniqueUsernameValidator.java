package com.notes.manager.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.notes.manager.domain.User;
import com.notes.manager.service.UserService;

@Component
public class UniqueUsernameValidator implements Validator{
	@Autowired
	private UserService userService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User targetUser = (User) target;

		if(userService.userWithThisUsernameExist(targetUser.getUsername())){
			errors.rejectValue("username", "", "This username is already in use");
		}
	}
}
