package com.example.imaginnovate.employee;

import com.example.imaginnovate.employee.controller.EmployeeController;
import com.example.imaginnovate.employee.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setEmployeeId("E123");
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");

        List<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add("1234567890");
        employee.setPhoneNumbers(phoneNumbers);

        employee.setDoj(LocalDate.now());
        employee.setSalary(50000);

        // Convert employee object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String employeeJson = objectMapper.writeValueAsString(employee);

        // Perform POST request
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isOk()); // Expect a 200 OK status
    }

    @Test
    public void testCreateEmployeeInvalidId() throws Exception {
        Employee employee = new Employee();
        employee.setEmployeeId("123"); // Invalid ID
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");

        List<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add("1234567890");
        employee.setPhoneNumbers(phoneNumbers);

        employee.setDoj(LocalDate.now());
        employee.setSalary(50000);

        // Convert employee object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String employeeJson = objectMapper.writeValueAsString(employee);

        // Perform POST request
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isBadRequest()); // Expect a 400 Bad Request status
    }

    @Test
    public void testCreateEmployeeInvalidPhoneNumber() throws Exception {
        Employee employee = new Employee();
        employee.setEmployeeId("E123");
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");

        List<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add("12345678"); // Invalid phone number (not 10 digits)
        employee.setPhoneNumbers(phoneNumbers);

        employee.setDoj(LocalDate.now());
        employee.setSalary(50000);

        // Convert employee object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String employeeJson = objectMapper.writeValueAsString(employee);

        // Perform POST request
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isBadRequest()); // Expect a 400 Bad Request status
    }

}
