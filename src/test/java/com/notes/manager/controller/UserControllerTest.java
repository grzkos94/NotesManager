package com.notes.manager.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.notes.manager.domain.User;
import com.notes.manager.service.UserService;
import com.notes.manager.validator.UserValidator;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class UserControllerTest {
	UserController controller;
	MockMvc mockMvc;
	UserService mockUserService;

	@Autowired
	UserValidator userValidator;
	
	@Before
	public void setUp() {
		mockUserService = mock(UserService.class);
		controller = new UserController(mockUserService, userValidator);
		mockMvc = standaloneSetup(controller).build();
	}

	@Test
	public void shouldRedirectToLoginForm() throws Exception {
		mockMvc.perform(get("/someurl")).andExpect(view().name("redirect:/login"));
	}

	@Test
	public void shouldReturnLoginForm() throws Exception {
		mockMvc.perform(get("/login")).andExpect(view().name("user/login"));
	}

	@Test
	public void shouldReturnLoginFromWithError() throws Exception {
		mockMvc.perform(get("/login-error")).andExpect(view().name("user/login"))
				.andExpect(model().attributeExists("loginError")).andExpect(model().attribute("loginError", is(true)));
	}

	@Test
	public void shouldReturnFormToRegisterUser() throws Exception {
		mockMvc.perform(get("/register")).andExpect(view().name("user/register"))
				.andExpect(model().attributeExists("user"))
				.andExpect(model().attribute("user", instanceOf(User.class)));
	}

	@Test
	public void shouldRedirectToLoginForm_whenRegisteringUser() throws Exception {
		mockMvc.perform(post("/register").param("username", "user12345").param("password", "password1").param("passwordConfirm", "password1"))
				.andExpect(view().name("redirect:/login")).andExpect(flash().attribute("alertSuccess", "User created"));
		verify(mockUserService, times(1)).save(any(User.class));
	}

	@Test
	public void shouldReturnFormToRegisterUser_whenProvidingWrongData() throws Exception {
		mockMvc.perform(post("/register").param("username", "wrong value").param("password", ""))
				.andExpect(view().name("user/register"));
	}
}
