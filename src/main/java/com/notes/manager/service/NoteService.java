package com.notes.manager.service;

import java.util.List;

import com.notes.manager.domain.Note;
import com.notes.manager.domain.User;

public interface NoteService {
	List<Note> findAllByUser(User user);

	Note findOne(long noteId, long userId);

	void save(Note note, User user);

	void update(long originalNoteId, Note noteWithNewValues, User user);

	void delete(long noteId, long userId);
}
