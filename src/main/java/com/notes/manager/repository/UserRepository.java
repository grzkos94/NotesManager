package com.notes.manager.repository;

import org.springframework.data.repository.CrudRepository;

import com.notes.manager.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);
}
