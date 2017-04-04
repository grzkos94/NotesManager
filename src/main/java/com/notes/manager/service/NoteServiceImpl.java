package com.notes.manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notes.manager.domain.Note;
import com.notes.manager.domain.User;
import com.notes.manager.exception.NoteNotFoundException;
import com.notes.manager.repository.NoteRepository;

@Service
public class NoteServiceImpl implements NoteService {
	@Autowired
	private NoteRepository noteRepository;

	@Override
	public List<Note> findAllByUser(User user) {
		return noteRepository.findAllByUser(user);
	}

	@Override
	public Note findOne(long noteId, long userId) throws NoteNotFoundException {
		Note note = noteRepository.findOne(noteId);

		if (note == null || note.getUser().getId() != userId) {
			throw new NoteNotFoundException(noteId);
		}

		return note;
	}

	@Override
	public void update(long originalNoteId, Note noteWithNewValues, User user) throws NoteNotFoundException {
		Note originalNote = findOne(originalNoteId, user.getId());

		originalNote.setTitle(noteWithNewValues.getTitle());
		originalNote.setContent(noteWithNewValues.getContent());

		noteRepository.save(originalNote);
	}

	@Override
	public void save(Note note, User user) {
		note.setUser(user);
		noteRepository.save(note);
	}

	@Override
	public void delete(long noteId, long userId) throws NoteNotFoundException {
		noteRepository.delete(findOne(noteId, userId));
	}
}
