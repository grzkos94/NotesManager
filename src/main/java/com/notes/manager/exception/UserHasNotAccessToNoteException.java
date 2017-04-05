package com.notes.manager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "This note not belongs to you")
public class UserHasNotAccessToNoteException extends RuntimeException {
}
