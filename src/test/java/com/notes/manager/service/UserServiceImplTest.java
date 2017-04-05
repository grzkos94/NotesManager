package com.notes.manager.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.notes.manager.domain.User;
import com.notes.manager.exception.UserNotFoundException;
import com.notes.manager.repository.UserRepository;

public class UserServiceImplTest {
	UserRepository mockUserRepository = mock(UserRepository.class);
	PasswordEncoder mockPasswordEncoder = mock(PasswordEncoder.class);
	UserServiceImpl userService = new UserServiceImpl(mockUserRepository, mockPasswordEncoder);

	@Test
	public void shouldSaveUser() {
		User userToSave = new User();
		userToSave.setPassword("password");
		when(mockPasswordEncoder.encode("password")).thenReturn("encoded password");

		userService.save(userToSave);

		verify(mockUserRepository, times(1)).save(userToSave);
		assertEquals("encoded password", userToSave.getPassword());
	}

	@Test
	public void shouldReturnFoundUser() {
		User userToReturn = new User();
		userToReturn.setUsername("user");
		when(mockUserRepository.findByUsername(userToReturn.getUsername())).thenReturn(userToReturn);

		User returnedUser = userService.findByUsername(userToReturn.getUsername());

		assertEquals(userToReturn, returnedUser);
	}

	@Test(expected = UserNotFoundException.class)
	public void shouldThrowException_whenNotFoundUser() {
		when(mockUserRepository.findByUsername("wrong username")).thenReturn(null);

		userService.findByUsername("wrong username");
	}
}
