package com.example.restservice;

import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.atomic.AtomicLong;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = GreetingController.class)
class RestServiceApplicationTests {

	@Autowired
	private MockMvc mvc;

	static AtomicLong counter = new AtomicLong();
	int id = (int) counter.incrementAndGet();

	@Test
	public void GreetingDto가_리턴된다_1() throws Exception{

		mvc.perform(get("/greeting"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(id)))
				.andExpect(jsonPath("$.content", is("Hello, World!")));

	}

	@Test
	public void GreetingDto가_리턴된다_2() throws Exception{
		String name = "spring";

		mvc.perform(get("/greeting")
		.param("name", "spring"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(id)))
				.andExpect(jsonPath("$.content", is("Hello, spring!")));

	}
}
