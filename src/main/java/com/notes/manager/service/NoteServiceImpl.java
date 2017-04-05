package com.notes.manager.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notes.manager.domain.Note;
import com.notes.manager.domain.User;
import com.notes.manager.exception.NoteNotFoundException;
import com.notes.manager.exception.UserHasNotAccessToNoteException;
import com.notes.manager.repository.NoteRepository;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {
	private NoteRepository noteRepository;

	@Autowired
	public NoteServiceImpl(NoteRepository noteRepository){
		this.noteRepository = noteRepository;
	}
	
	@Override
	public List<Note> findAllByUser(User user) {
		return noteRepository.findAllByUser(user);
	}

	@Override
	public Note findOne(long noteId, long userId){
		Note note = noteRepository.findOne(noteId);

		if (note == null)
			throw new NoteNotFoundException();

		if(!hasUserAccessToNote(note, userId))
			throw new UserHasNotAccessToNoteException();
			
		return note;
	}

	@Override
	public void update(long originalNoteId, Note noteWithNewValues, User user){
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
	public void delete(long noteId, long userId){
		noteRepository.delete(findOne(noteId, userId));
	}
	
	private boolean hasUserAccessToNote(Note note, long userId){
		if(note.getUser() == null || note.getUser().getId() != userId)
			return false;
		
		return true;
	}
}
