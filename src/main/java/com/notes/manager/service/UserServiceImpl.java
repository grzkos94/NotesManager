package com.notes.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.notes.manager.domain.User;
import com.notes.manager.domain.UserRole;
import com.notes.manager.repository.UserRepository;
import com.notes.manager.repository.UserRoleRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void save(User user) {
		user.setEnabled(true);
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		userRepository.save(user);
		userRoleRepository.save(new UserRole("ROLE_USER", user));
	}

	@Override
	public void update(User user) {
		userRepository.save(user);
	}

	@Override
	public User findById(long id) {
		return userRepository.findOne(id);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}
