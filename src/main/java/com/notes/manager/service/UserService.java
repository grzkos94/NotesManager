package com.notes.manager.service;

import com.notes.manager.domain.User;

public interface UserService {
	public void save(User user);

	public User findByUsername(String username);
	
	public boolean userWithThisUsernameExist(String username);
}
