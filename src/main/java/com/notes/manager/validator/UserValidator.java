package com.notes.manager.validator;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.notes.manager.domain.User;

public class UserValidator implements Validator {
	@Autowired
	private javax.validation.Validator beanValidator;

	private Set<Validator> springValidators;

	public UserValidator() {
		springValidators = new HashSet<>();
	}

	public void setSpringValidators(Set<Validator> springValidators) {
		this.springValidators = springValidators;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Set<ConstraintViolation<Object>> constraintValidators = beanValidator.validate(target);

		for (ConstraintViolation<Object> constraintViolation : constraintValidators) {
			String propertyPath = constraintViolation.getPropertyPath().toString();
			String message = constraintViolation.getMessage();
			errors.rejectValue(propertyPath, "", message);
		}

		for (Validator validator : springValidators)
			validator.validate(target, errors);
	}
}
