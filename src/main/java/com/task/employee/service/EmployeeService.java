package com.task.employee.service;

import com.task.employee.dto.EmployeeDto;

import com.task.employee.entity.Employee;
import com.task.employee.event.EmployeeEvent;
import com.task.employee.exception.EmailAlreadyInUseException;
import com.task.employee.repository.EmployeeRepository;
import com.task.employee.sender.RabbitMQSender;
import com.task.employee.util.HobbiesConvertor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final RabbitMQSender rabbitMQSender;
    private HobbiesConvertor hobbiesConvertor = new HobbiesConvertor();

    public EmployeeService(EmployeeRepository employeeRepository, RabbitMQSender rabbitMQSender) {
        this.employeeRepository = employeeRepository;
        this.rabbitMQSender = rabbitMQSender;
    }

    @Transactional
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        if (employeeRepository.findByEmail(employeeDto.getEmail()).isPresent()) {
            throw new EmailAlreadyInUseException("This email is already used with different employee: " + employeeDto.getEmail());
        }
        EmployeeDto createdEmployee = employeeRepository.save(employeeDto.toEntity()).toEmployeeDto();
        rabbitMQSender.sendEmployeeCreatedEvent(createdEmployee.toEvent());
        return createdEmployee;
    }

    public Optional<Employee> getEmployeeById(UUID employeeId) {
        return employeeRepository.findById(employeeId);
    }

    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll().stream().map(Employee::toEmployeeDto).collect(Collectors.toList());
    }

    @Transactional
    public void deleteEmployeeById(UUID employeeId, EmployeeDto employee) {
        employeeRepository.deleteById(employeeId);
        rabbitMQSender.sendEmployeeDeletedEvent(employee.toEvent());
    }


    @Transactional
    public EmployeeDto updateEmployee(Employee employee, EmployeeDto employeeDto) {

        if (employeeDto.getFirstName() != null) {
            employee.setFirstName(employeeDto.getFirstName());
        }
        if (employeeDto.getLastName() != null) {
            employee.setLastName(employeeDto.getLastName());
        }
        if (employeeDto.getEmail() != null) {
            if (employeeRepository.findByEmail(employeeDto.getEmail()).isPresent()) {
                throw new EmailAlreadyInUseException("This email is already used with different employee: " + employeeDto.getEmail());
            } else {
                employee.setEmail(employeeDto.getEmail());
            }
        }
        if (employeeDto.getBirthday() != null) {
            employee.setBirthday(LocalDate.parse(employeeDto.getBirthday()));
        }
        if (employeeDto.getHobbies() != null) {
            employee.setHobbies(hobbiesConvertor.fromDtoToEntity(employeeDto.getHobbies()));
        }
        EmployeeDto updatedEmployee = employeeRepository.save(employee).toEmployeeDto();
        rabbitMQSender.sendEmployeeUpdatedEvent(updatedEmployee.toEvent());
        return updatedEmployee;
    }
}
