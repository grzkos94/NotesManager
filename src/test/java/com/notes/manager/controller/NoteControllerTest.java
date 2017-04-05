package com.notes.manager.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import com.notes.manager.domain.Note;
import com.notes.manager.service.NoteService;
import com.notes.manager.service.UserService;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.ArrayList;
import java.util.List;

public class NoteControllerTest {
	NoteController controller;
	NoteService mockNoteService;
	UserService mockUserService;
	
	@Before
	public void setUp(){
		mockNoteService = mock(NoteService.class);
		mockUserService = mock(UserService.class);
		
		controller = new NoteController(mockNoteService, mockUserService);
	}
	
	@Test
	public void testIndexPage() throws Exception{
		List<Note> notes = createNotesList();
		MockMvc mockMvc = standaloneSetup(controller).build();
		
		when(mockNoteService.findAllByUser(any())).thenReturn(notes);
		
		mockMvc.perform(get("/notes/index"))
			.andExpect(model().attributeExists("notes"))
			.andExpect(model().attribute("notes", equalTo(notes)));
	}
	
	private List<Note> createNotesList(){
		List<Note> notes = new ArrayList<>();
		notes.add(new Note());
		notes.add(new Note());

		return notes;
	}
}
