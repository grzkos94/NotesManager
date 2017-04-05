package com.notes.manager.service;

import org.junit.Before;
import org.junit.Test;

import com.notes.manager.domain.Note;
import com.notes.manager.domain.User;
import com.notes.manager.exception.NoteNotFoundException;
import com.notes.manager.exception.UserHasNotAccessToNoteException;
import com.notes.manager.repository.NoteRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

public class NoteServiceImplTest {
	NoteRepository mockNoteRepository = mock(NoteRepository.class)	;
	NoteServiceImpl noteService = new NoteServiceImpl(mockNoteRepository);

	Note note1;
	Note note2;

	@Before
	public void setUp() {
		note1 = new Note();
		note1.setId(2L);
		note1.setUser(new User());
		note1.getUser().setId(3L);

		note2 = new Note();
		note2.setId(4L);
		note2.setUser(new User());
		note2.getUser().setId(5L);
	}

	@Test
	public void shouldReturnListOfNotes() {
		List<Note> notes = createNotesList();
		User user = new User();

		when(mockNoteRepository.findAllByUser(user)).thenReturn(notes);

		assertEquals(notes, noteService.findAllByUser(user));
	}

	@Test
	public void shouldReturnFoundNote() {
		when(mockNoteRepository.findOne(note1.getId())).thenReturn(note1);

		assertEquals(note1, noteService.findOne(note1.getId(), note1.getUser().getId()));
	}

	@Test(expected = UserHasNotAccessToNoteException.class)
	public void shouldThrowException_whenFoundNotOwnedNote() {
		when(mockNoteRepository.findOne(note1.getId())).thenReturn(note2);

		noteService.findOne(note1.getId(), note1.getUser().getId());
	}

	@Test(expected = NoteNotFoundException.class)
	public void shouldThrowException_whenNotFoundNote() {
		when(mockNoteRepository.findOne(note1.getId())).thenReturn(null);

		noteService.findOne(note1.getId(), note1.getUser().getId());
	}

	@Test
	public void shouldSaveNote() {
		User user = new User();
		Note noteToSave = new Note();

		noteService.save(noteToSave, user);

		verify(mockNoteRepository, times(1)).save(noteToSave);
		assertEquals(user, noteToSave.getUser());
	}

	@Test
	public void shouldDeleteNote() {
		when(mockNoteRepository.findOne(note1.getId())).thenReturn(note1);

		noteService.delete(note1.getId(), note1.getUser().getId());

		verify(mockNoteRepository, times(1)).delete(note1);
	}

	@Test(expected = UserHasNotAccessToNoteException.class)
	public void shouldThrowException_whenTryingToDeleteNotOwnedNote() {
		when(mockNoteRepository.findOne(note1.getId())).thenReturn(note2);

		noteService.delete(note1.getId(), note1.getUser().getId());
	}

	@Test(expected = NoteNotFoundException.class)
	public void shouldThrowException_whenTryingToDeleteNotFoundNote() {
		when(mockNoteRepository.findOne(note1.getId())).thenReturn(null);

		noteService.delete(note1.getId(), note1.getUser().getId());
	}

	@Test
	public void shouldUpdateNote() {
		Note noteWithNewValues = new Note("new title", "new content");
		when(mockNoteRepository.findOne(note1.getId())).thenReturn(note1);

		noteService.update(note1.getId(), noteWithNewValues, note1.getUser());

		assertEquals(noteWithNewValues.getTitle(), note1.getTitle());
		assertEquals(noteWithNewValues.getContent(), note1.getContent());
		verify(mockNoteRepository, times(1)).save(note1);
	}

	@Test(expected = UserHasNotAccessToNoteException.class)
	public void shouldThrowException_whenTryingToUpdateNotOwnedNote() {
		Note noteWithNewValues = new Note("new title", "new content");
		when(mockNoteRepository.findOne(note1.getId())).thenReturn(note2);

		noteService.update(note1.getId(), noteWithNewValues, note1.getUser());
	}

	@Test(expected = NoteNotFoundException.class)
	public void shouldThrowException_whenTryingToUpdateNotFoundNote() {
		when(mockNoteRepository.findOne(note1.getId())).thenReturn(null);
		Note noteWithNewValues = new Note("new title", "new content");

		noteService.update(note1.getId(), noteWithNewValues, note1.getUser());
	}

	private List<Note> createNotesList() {
		List<Note> notes = new ArrayList<>();
		notes.add(new Note());
		notes.add(new Note());

		return notes;
	}
}
