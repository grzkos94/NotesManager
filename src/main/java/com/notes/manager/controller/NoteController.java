package com.notes.manager.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.notes.manager.domain.Note;
import com.notes.manager.domain.User;
import com.notes.manager.exception.NoteNotFoundException;
import com.notes.manager.service.NoteService;
import com.notes.manager.service.UserService;

@Controller
@RequestMapping("/notes")
public class NoteController {
	@Autowired
	private NoteService noteService;
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model, @ModelAttribute("currentUser") User currentUser) {
		model.addAttribute("notes", noteService.findAllByUser(currentUser));
		return "note/index";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("note", new Note());
		return "note/create";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String doCreate(@ModelAttribute("currentUser") User currentUser, @Valid Note note,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors())
			return "note/create";

		noteService.save(note, currentUser);
		redirectAttributes.addFlashAttribute("alertSuccess", "Note created");

		return "redirect:/notes/index";
	}

	@RequestMapping(value = "/update/{noteId}", method = RequestMethod.GET)
	public String update(@PathVariable long noteId, @ModelAttribute("currentUser") User currentUser, Model model)
			throws NoteNotFoundException {

		model.addAttribute("note", noteService.findOne(noteId, currentUser.getId()));
		return "note/update";
	}

	@RequestMapping(value = "/update/{noteId}", method = RequestMethod.POST)
	public String doUpdate(@PathVariable long noteId, @ModelAttribute("currentUser") User currentUser, @Valid Note note,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) throws NoteNotFoundException {

		if (bindingResult.hasErrors()) {
			note.setId(noteId);
			return "note/update";
		}

		noteService.update(noteId, note, currentUser);
		redirectAttributes.addFlashAttribute("alertSuccess", "Note updated");

		return "redirect:/notes/index";
	}

	@RequestMapping(value = "/delete/{noteId}", method = RequestMethod.GET)
	public String doDelete(@PathVariable long noteId, @ModelAttribute("currentUser") User currentUser,
			RedirectAttributes redirectAttributes) throws Exception {

		noteService.delete(noteId, currentUser.getId());
		redirectAttributes.addFlashAttribute("alertSuccess", "Note deleted");

		return "redirect:/notes/index";
	}

	@InitBinder
	public void initialiseBinder(WebDataBinder binder) {
		binder.setAllowedFields("title", "content");
	}
	
	@ModelAttribute("currentUser")
	public User user(Principal principal){
		if(principal == null) return null;

		return userService.findByUsername(principal.getName());
	}
}
