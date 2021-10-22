package com.marlabs.training.project;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marlabs.training.project.controller.ProjectController;
import com.marlabs.training.project.entity.Project;
import com.marlabs.training.project.entity.StatusEnum;
import com.marlabs.training.project.service.ProjectService;



@WebMvcTest(ProjectController.class)
public class ManagementFlowApplicationControllerTest {

	LocalDateTime dateCreated = LocalDateTime.now();

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private ProjectService projectService;

	@Test

	public void getAllProjectTest() throws Exception {
		List<Project> project = new ArrayList<>();
		project.add(new Project(1L, "Test1", StatusEnum.FINISHED, dateCreated, false));
		project.add(new Project(2L, "Test2", StatusEnum.INPROGRESS, dateCreated, false));

		when(projectService.findByArchived(false)).thenReturn(project);
		String url = "/projects/";

		MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();

		String actualResult = mvcResult.getResponse().getContentAsString();
		System.out.println(actualResult);

		String expectedResult = objectMapper.writeValueAsString(project);

		assertThat(actualResult).isEqualToIgnoringWhitespace(expectedResult);

	}

	@Test

	public void createProjectTest() throws JsonProcessingException, Exception {
		Project newProject = new Project(1L, "Test1", StatusEnum.FINISHED, dateCreated, false);
		when(projectService.createProject(newProject)).thenReturn(newProject);
		String url = "/projects/";

		mockMvc.perform(post(url).contentType("application/json").content(objectMapper.writeValueAsString(newProject))

				.with(csrf())).andExpect(status().isOk());
	}

	@Test
	public void updateProjectTest() throws JsonProcessingException, Exception {
		Project existProject = new Project(1L, "Test1", StatusEnum.FINISHED, dateCreated, false);
		when(projectService.updateProjectById(1L)).thenReturn(existProject);
		String url = "/projects/1";

		mockMvc.perform(put(url).contentType("application/json").content(objectMapper.writeValueAsString(existProject))

				.with(csrf())).andExpect(status().isOk());
	}

}
