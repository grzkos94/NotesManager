package com.notes.manager.service;

import com.notes.manager.domain.User;

public interface UserService {
	public void save(User user);

	public void update(User user);

	public User findById(long id);

	public User findByUsername(String username);
}
