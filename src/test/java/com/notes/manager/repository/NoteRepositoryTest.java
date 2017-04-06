package com.notes.manager.repository;

import static org.assertj.core.api.Assertions.assertThat;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.notes.manager.domain.Note;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class NoteRepositoryTest {
	@Autowired
	private NoteRepository noteRepository;

	private static final String DEFAULT_TITLE = "title1";
	private static final String DEFAULT_CONTENT = "content1";

	@Test
	public void shouldHaveIdAfterSavingToDb(){
		Note noteToSave = new Note(DEFAULT_TITLE, DEFAULT_CONTENT);
		
		noteRepository.save(noteToSave);
		
		assertThat(noteToSave.getId()).isGreaterThan(0);
	}
	
	@Test
	public void shouldFindNoteById(){
		Note note1 = new Note(DEFAULT_TITLE, DEFAULT_CONTENT);
		noteRepository.save(note1);
		
		Note note2 = noteRepository.findOne(note1.getId());
		
		assertThat(note2).isNotNull();
	}
	
	@Test
	public void shouldHaveTheSameTitleAndContentAfterSavingToDb(){
		Note note1 = new Note(DEFAULT_TITLE, DEFAULT_CONTENT);
		
		noteRepository.save(note1);
		
		Note note2 = noteRepository.findOne(note1.getId());
		assertThat(note2.getTitle()).isEqualTo("title1");
		assertThat(note2.getContent()).isEqualTo("content1");
	}
	
	@Test
	public void shouldUpdateTitleAndContentCorrectly(){
		Note note1 = new Note(DEFAULT_TITLE, DEFAULT_CONTENT);
		noteRepository.save(note1);
		note1.setTitle("title2");
		note1.setContent("content2");
		
		noteRepository.save(note1);
		
		Note note2 = noteRepository.findOne(note1.getId());
		assertThat(note2.getTitle()).isEqualTo("title2");
		assertThat(note2.getContent()).isEqualTo("content2");
	}
	
	@Test
	public void shouldDeleteNoteFromDb(){
		Note note1 = new Note(DEFAULT_TITLE, DEFAULT_CONTENT);
		noteRepository.save(note1);
		
		noteRepository.delete(note1.getId());
		Note note2 = noteRepository.findOne(note1.getId());
		assertThat(note2).isNull();
	}
}
