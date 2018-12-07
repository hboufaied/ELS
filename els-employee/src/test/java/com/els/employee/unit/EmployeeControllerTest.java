package com.els.employee.unit;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.els.employee.ElsEmployeeApplication;
import com.els.employee.controller.EmployeeController;
import com.els.employee.service.EmployeeService;

/**
 * @author h.boufaied
 *
 */
@WebAppConfiguration
@ContextConfiguration(classes = ElsEmployeeApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class EmployeeControllerTest {

	private MockMvc mockMvc;

	@Autowired
	EmployeeService employeeService;

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(new EmployeeController(employeeService)).build();
	}

	@Test
	public void wrongDataOnJsonFile() throws Exception {
		MockMultipartFile jsonFile = new MockMultipartFile("file", "", "application/json",
				"{\"json\": \"wrongData\"}".getBytes());
		mockMvc.perform(fileUpload("/api/v1/employee/upload").file(jsonFile)).andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	public void simpleDataOnJsonFile() throws Exception {
		MockMultipartFile jsonFile = new MockMultipartFile("file", "employee.json", "application/json",
				"[{\"firstName\": \"Hamda\",\"lastName\": \"BOUFAIED\",\"email\": \"hboufaied@gmail.com\",\"phone\": \"0021655667788\",\"passportNumber\": \"TN50607080\"}]"
						.getBytes());
		mockMvc.perform(fileUpload("/api/v1/employee/upload").file(jsonFile)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.fileName", is("employee.json")))
				.andExpect(jsonPath("$.initialEmployeeNumber", is(1)))
				.andExpect(jsonPath("$.finalEmployeeNumber", is(1)));
	}

	@Test
	public void duplicateDataOnJsonFile() throws Exception {
		MockMultipartFile jsonFile = new MockMultipartFile("file", "employee.json", "application/json",
				"[{\"firstName\": \"Hamda\",\"lastName\": \"BOUFAIED\",\"email\": \"hboufaied@gmail.com\",\"phone\": \"0021655667788\",\"passportNumber\": \"TN50607080\"},{\"firstName\": \"Hamda\",\"lastName\": \"BOUFAIED\",\"email\": \"hboufaied@gmail.com\",\"phone\": \"0021655667788\",\"passportNumber\": \"TN50607080\"}]"
						.getBytes());
		mockMvc.perform(fileUpload("/api/v1/employee/upload").file(jsonFile)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.fileName", is("employee.json")))
				.andExpect(jsonPath("$.initialEmployeeNumber", is(2)))
				.andExpect(jsonPath("$.finalEmployeeNumber", is(1)));
	}
}