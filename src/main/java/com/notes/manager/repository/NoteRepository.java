package com.notes.manager.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.notes.manager.domain.Note;
import com.notes.manager.domain.User;

public interface NoteRepository extends CrudRepository<Note, Long> {
	List<Note> findAllByUser(User user);
}
