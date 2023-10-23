import com.task.employee.dto.EmployeeDto;
import com.task.employee.entity.Employee;
import com.task.employee.event.EmployeeEvent;
import com.task.employee.repository.EmployeeRepository;
import com.task.employee.sender.RabbitMQSender;
import com.task.employee.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private RabbitMQSender rabbitMQSender;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        employeeService = new EmployeeService(employeeRepository, rabbitMQSender);
    }

    @Test
    void createEmployee() {

        EmployeeDto employeeDto = new EmployeeDto(UUID.randomUUID(), "John", "Doe", "john@example.com", "2022-12-14", Collections.emptyList());
        Employee createdEmployee = new Employee(UUID.randomUUID(), "John", "Doe", "john@example.com", LocalDate.parse("2022-12-14"), "");
        Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(createdEmployee);


        EmployeeDto result = employeeService.createEmployee(employeeDto);


        Mockito.verify(rabbitMQSender, Mockito.times(1)).sendEmployeeCreatedEvent(Mockito.any(EmployeeEvent.class));
        assertEquals(result.getFirstName(), createdEmployee.getFirstName());

    }

    @Test
    void getEmployeeById() {

        UUID employeeId = UUID.randomUUID();
        Employee expectedEmployee = new Employee(employeeId, "John", "Doe", "john@example.com", LocalDate.parse("2022-12-14"), "");
        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(expectedEmployee));


        Optional<Employee> result = employeeService.getEmployeeById(employeeId);

        assertEquals(result, Optional.of(expectedEmployee));
    }

    @Test
    void getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(UUID.randomUUID(), "John", "Doe", "john@example.com", LocalDate.parse("2022-12-14"), ""));
        Mockito.when(employeeRepository.findAll()).thenReturn(employees);


        List<EmployeeDto> result = employeeService.getAllEmployees();

        assertEquals(result.size(), employees.size());
    }

    @Test
    void deleteEmployeeById() {
        UUID employeeId = UUID.randomUUID();
        EmployeeDto employeeDto = new EmployeeDto();
        Mockito.doNothing().when(employeeRepository).deleteById(employeeId);

        employeeService.deleteEmployeeById(employeeId, employeeDto);

        Mockito.verify(rabbitMQSender, Mockito.times(1)).sendEmployeeDeletedEvent(Mockito.any(EmployeeEvent.class));
    }

    @Test
    void updateEmployee() {
        Employee existingEmployee = new Employee(UUID.randomUUID(), "John", "Doe", "john@example.com", LocalDate.parse("2022-12-14"), "");
        EmployeeDto employeeDto = new EmployeeDto();
        Mockito.when(employeeRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(existingEmployee);

        EmployeeDto result = employeeService.updateEmployee(existingEmployee, employeeDto);

        Mockito.verify(rabbitMQSender, Mockito.times(1)).sendEmployeeUpdatedEvent(Mockito.any(EmployeeEvent.class));

    }

}