package com.notes.manager.repository;

import org.springframework.data.repository.CrudRepository;

import com.notes.manager.domain.UserRole;

public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
}
