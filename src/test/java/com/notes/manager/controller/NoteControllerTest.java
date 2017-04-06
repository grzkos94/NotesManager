package com.notes.manager.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import com.notes.manager.domain.Note;
import com.notes.manager.domain.User;
import com.notes.manager.service.NoteService;
import com.notes.manager.service.UserService;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

public class NoteControllerTest {
	NoteController controller;
	NoteService mockNoteService;
	UserService mockUserService;
	Principal mockPrincipal;
	User currentUser;
	MockMvc mockMvc;

	@Before
	public void setUp() {
		mockNoteService = mock(NoteService.class);
		mockUserService = mock(UserService.class);
		mockPrincipal = mock(Principal.class);
		currentUser = new User();
		currentUser.setId(1L);
		controller = new NoteController(mockNoteService, mockUserService);
		mockMvc = standaloneSetup(controller).build();

	}

	@Test
	public void shouldReturnIndexWithNotes() throws Exception {
		List<Note> notes = createNotesList();
		when(mockPrincipal.getName()).thenReturn("user1");
		when(mockUserService.findByUsername("user1")).thenReturn(currentUser);
		when(mockNoteService.findAllByUser(currentUser)).thenReturn(notes);

		mockMvc.perform(get("/notes/index").principal(mockPrincipal)).andExpect(model().attributeExists("notes"))
				.andExpect(model().attribute("notes", equalTo(notes)));
	}

	@Test
	public void shouldReturnFormToCreateNote() throws Exception {
		mockMvc.perform(get("/notes/create")).andExpect(model().attributeExists("note"))
				.andExpect(model().attribute("note", instanceOf(Note.class)));
	}

	@Test
	public void shouldRedirectToIndex_whenCreatingNote() throws Exception {
		when(mockPrincipal.getName()).thenReturn("user1");
		when(mockUserService.findByUsername("user1")).thenReturn(currentUser);
		mockMvc.perform(post("/notes/create").principal(mockPrincipal).param("title", "some title").param("content",
				"some content")).andExpect(view().name("redirect:/notes/index"))
				.andExpect(flash().attribute("alertSuccess", "Note created"));

		verify(mockNoteService, times(1)).save(any(Note.class), eq(currentUser));
	}

	@Test
	public void shouldReturnFormToCreateNote_whenProvidingWrongData() throws Exception {
		mockMvc.perform(post("/notes/create").param("title", "some title").param("wrongAttr", "some content"))
				.andExpect(view().name("note/create"));
	}

	@Test
	public void shouldReturnFormToUpdateNote() throws Exception {
		when(mockPrincipal.getName()).thenReturn("user1");
		when(mockUserService.findByUsername("user1")).thenReturn(currentUser);
		when(mockNoteService.findOne(1L, 1L)).thenReturn(new Note());

		mockMvc.perform(get("/notes/update/1").principal(mockPrincipal)).andExpect(view().name("note/update"));
	}

	@Test
	public void shouldRedirectToIndex_whenUpdatingNote() throws Exception {
		when(mockPrincipal.getName()).thenReturn("user1");
		when(mockUserService.findByUsername("user1")).thenReturn(currentUser);
		when(mockNoteService.findOne(1L, 1L)).thenReturn(new Note());

		mockMvc.perform(post("/notes/update/1").param("title", "new title").param("content", "new content")
				.principal(mockPrincipal)).andExpect(view().name("redirect:/notes/index"))
				.andExpect(flash().attribute("alertSuccess", "Note updated"));
		verify(mockNoteService, times(1)).update(eq(1L), any(Note.class), eq(currentUser));
	}

	@Test
	public void shouldReturnFormToUpdateNote_whenProvidingWrongData() throws Exception {
		mockMvc.perform(post("/notes/update/1").param("wrongAttr1", "wrong value").param("wrongAttr2", "wrong value"))
				.andExpect(view().name("note/update"));
	}

	@Test
	public void shouldRedirectToIndex_whenDeletingNote() throws Exception {
		Note noteToDelete = new Note();
		noteToDelete.setId(1L);
		when(mockPrincipal.getName()).thenReturn("user1");
		when(mockUserService.findByUsername("user1")).thenReturn(currentUser);
		when(mockNoteService.findOne(1L, 1L)).thenReturn(noteToDelete);

		mockMvc.perform(get("/notes/delete/1").principal(mockPrincipal)).andExpect(view().name("redirect:/notes/index"))
				.andExpect(flash().attribute("alertSuccess", "Note deleted"));
		verify(mockNoteService, times(1)).delete(eq(noteToDelete.getId()), eq(currentUser.getId()));
	}

	private List<Note> createNotesList() {
		List<Note> notes = new ArrayList<>();
		notes.add(new Note());
		notes.add(new Note());

		return notes;
	}
}
