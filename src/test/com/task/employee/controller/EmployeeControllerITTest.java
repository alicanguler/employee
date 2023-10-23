package com.task.employee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.employee.dto.EmployeeDto;
import com.task.employee.entity.Employee;
import com.task.employee.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerITTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeService employeeService;

    private List<EmployeeDto> employeeDtoList;

    @BeforeEach
    public void setup() {
        employeeDtoList = new ArrayList<>();
        EmployeeDto employee1 = new EmployeeDto();
        employee1.setId(UUID.randomUUID());
        employee1.setFirstName("Ali");
        employee1.setLastName("Can");
        employee1.setBirthday("1999-07-07");
        employeeDtoList.add(employee1);
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(employeeDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employee")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testGetEmployeeById() throws Exception {
        UUID employeeId = employeeDtoList.get(0).getId();
        EmployeeDto employeeDto= employeeDtoList.get(0);
        when(employeeService.getEmployeeById(employeeId)).thenReturn(Optional.of(employeeDto.toEntity()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employee/" + employeeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser
    public void testCreateEmployee() throws Exception {
        EmployeeDto newEmployee = new EmployeeDto();
        newEmployee.setFirstName("Jane");
        newEmployee.setLastName("Smith");
        UUID employeeId = UUID.randomUUID();

        when(employeeService.createEmployee(newEmployee)).thenReturn(employeeDtoList.get(0));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newEmployee)))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    @WithMockUser
    public void testUpdateEmployee() throws Exception {
        UUID employeeId = employeeDtoList.get(0).getId();
        EmployeeDto updatedEmployee = new EmployeeDto();
        updatedEmployee.setFirstName("Jane");
        updatedEmployee.setLastName("Smith");
        EmployeeDto employeeDto= employeeDtoList.get(0);
        when(employeeService.getEmployeeById(employeeId)).thenReturn(Optional.of(employeeDto.toEntity()));
        when(employeeService.updateEmployee(employeeDto.toEntity(), updatedEmployee)).thenReturn(updatedEmployee);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/employee/" + employeeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedEmployee)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser
    public void testDeleteEmployee() throws Exception {
        UUID employeeId = employeeDtoList.get(0).getId();
        EmployeeDto employeeDto= employeeDtoList.get(0);
        when(employeeService.getEmployeeById(employeeId)).thenReturn(Optional.of(employeeDto.toEntity()));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/employee/" + employeeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}