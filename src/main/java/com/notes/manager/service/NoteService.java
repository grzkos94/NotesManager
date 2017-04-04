package com.notes.manager.service;

import java.util.List;

import com.notes.manager.domain.Note;
import com.notes.manager.domain.User;
import com.notes.manager.exception.NoteNotFoundException;

public interface NoteService {
	List<Note> findAllByUser(User user);

	Note findOne(long noteId, long userId) throws NoteNotFoundException;

	void save(Note note, User user);

	void update(long originalNoteId, Note noteWithNewValues, User user) throws NoteNotFoundException;

	void delete(long noteId, long userId) throws NoteNotFoundException;
}
