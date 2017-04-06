package com.notes.manager;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.Validator;

import com.notes.manager.validator.PasswordConfirmationValidator;
import com.notes.manager.validator.UniqueUsernameValidator;
import com.notes.manager.validator.UserValidator;

@SpringBootApplication
public class NotesManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotesManagerApplication.class, args);
	}

	@Bean
	Validator passwordConfirmationValidator() {
		return new PasswordConfirmationValidator();
	}
	
	@Bean
	Validator uniqueUsernameValidator(){
		return new UniqueUsernameValidator();
	}

	@Bean
	UserValidator userValidator() {
		Set<Validator> springValidators = new HashSet<>();
		springValidators.add(passwordConfirmationValidator());
		springValidators.add(uniqueUsernameValidator());

		UserValidator validator = new UserValidator();
		validator.setSpringValidators(springValidators);

		return validator;
	}
}
