package fr.fms;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

@SpringBootTest
@AutoConfigureMockMvc
class ApiTrainingsTestApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void TestGetTrainingsAndTestName() throws Exception {
		mockMvc.perform(get("/api/trainings"))
				.andExpect(status().isOk())
				.andExpect((ResultMatcher) jsonPath("$[0].name", is("Java")));
	}

}
