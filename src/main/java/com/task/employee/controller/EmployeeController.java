package com.task.employee.controller;

import com.task.employee.dto.EmployeeDto;
import com.task.employee.entity.Employee;
import com.task.employee.exception.EmailAlreadyInUseException;
import com.task.employee.exception.InvalidDateFormatException;
import com.task.employee.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        return ResponseEntity.ok(service.getAllEmployees());
    }


    @GetMapping(value = "{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable UUID employeeId) {
        Optional<Employee> optionalEmployee = service.getEmployeeById(employeeId);
        return optionalEmployee.map(employee -> ResponseEntity.ok(employee.toEmployeeDto())).orElseGet(() ->
                ResponseEntity.notFound().build());

    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createEmployee(employeeDto));
    }

    @PatchMapping("{employeeId}")
    public ResponseEntity<?> updateEmployee(@PathVariable UUID employeeId, @Valid @RequestBody EmployeeDto employeeDto) {
        Optional<Employee> optionalEmployee = service.getEmployeeById(employeeId);
        return optionalEmployee.map(employee -> ResponseEntity.ok(service.updateEmployee(employee, employeeDto))).orElseGet(() -> ResponseEntity.noContent().build());

    }

    @DeleteMapping("{employeeId}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable UUID employeeId) {
        Optional<Employee> optionalEmployee = service.getEmployeeById(employeeId);
        if (optionalEmployee.isPresent()) {
            service.deleteEmployeeById(employeeId, optionalEmployee.get().toEmployeeDto());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Employee found with id:" + employeeId);
        }

    }

    @ExceptionHandler(InvalidDateFormatException.class)
    public ResponseEntity<String> handleInvalidDateFormat(InvalidDateFormatException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<String> handleEmailAlreadyInUse(EmailAlreadyInUseException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}
