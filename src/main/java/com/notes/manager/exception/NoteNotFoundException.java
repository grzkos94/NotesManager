package com.notes.manager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Note not found")
public class NoteNotFoundException extends Exception {
	public NoteNotFoundException(long noteId) {
		super("Note with ID = " + noteId + " does not exist");
	}
}