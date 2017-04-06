package com.notes.manager.repository;

import static org.assertj.core.api.Assertions.assertThat;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.notes.manager.domain.User;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class UserRepositoryTest {
	@Autowired
	private UserRepository userRepository;

	private static final String DEFAULT_USERNAME = "user1";
	private static final String DEFAULT_PASSWORD = "password1";

	@Test
	public void shouldHaveIdAfterSavingToDb() {
		User userToSave = new User(DEFAULT_USERNAME, DEFAULT_PASSWORD, DEFAULT_PASSWORD);

		userRepository.save(userToSave);

		assertThat(userToSave.getId()).isGreaterThan(0);
	}

	@Test
	public void shouldFindUserById() {
		User user1 = new User(DEFAULT_USERNAME, DEFAULT_PASSWORD, DEFAULT_PASSWORD);
		userRepository.save(user1);

		User user2 = userRepository.findOne(user1.getId());

		assertThat(user2).isNotNull();
	}
}
