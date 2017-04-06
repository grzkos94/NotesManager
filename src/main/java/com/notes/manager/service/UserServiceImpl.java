package com.notes.manager.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.notes.manager.domain.User;
import com.notes.manager.exception.UserNotFoundException;
import com.notes.manager.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		userRepository.save(user);
	}

	@Override
	public User findByUsername(String username) {
		User user = userRepository.findByUsername(username);
		
		if(user == null){
			throw new UserNotFoundException();
		}
		
		return user;
	}
	
	@Override
	public boolean userWithThisUsernameExist(String username){
		User user = userRepository.findByUsername(username);

		return user != null;
	}
}
