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

	@Test
	public void shouldHaveIdAfterSavingToDb() {
		User userToSave = new User();

		userRepository.save(userToSave);

		assertThat(userToSave.getId()).isGreaterThan(0);
	}
}
