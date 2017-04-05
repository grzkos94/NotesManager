package com.notes.manager.controller;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import com.notes.manager.domain.User;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

public class UserControllerTest {
	@Test
	public void testHomePage() throws Exception{
		UserController controller = new UserController();
		MockMvc mockMvc = standaloneSetup(controller).build();
		mockMvc.perform(get("/someurl")).andExpect(view().name("redirect:/login"));
	}
	
	@Test
	public void testLoginPage() throws Exception{
		UserController controller = new UserController();
		MockMvc mockMvc = standaloneSetup(controller).build();
		mockMvc.perform(get("/login")).andExpect(view().name("user/login"));
	}
	
	@Test
	public void testLoginErrorPage() throws Exception{
		UserController controller = new UserController();
		MockMvc mockMvc = standaloneSetup(controller).build();
		mockMvc.perform(get("/login-error"))
			.andExpect(view().name("user/login"))
			.andExpect(model().attributeExists("loginError"))
			.andExpect(model().attribute("loginError", is(true)));
	}
	
	@Test
	public void testRegisterPage() throws Exception{
		UserController controller = new UserController();
		MockMvc mockMvc = standaloneSetup(controller).build();
		mockMvc.perform(get("/register"))
		.andExpect(view().name("user/register"))
		.andExpect(model().attributeExists("user"))
		.andExpect(model().attribute("user", instanceOf(User.class)));
	}
}
