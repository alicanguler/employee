package com.task.employee.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.task.employee.entity.Employee;
import com.task.employee.event.EmployeeEvent;
import com.task.employee.exception.InvalidDateFormatException;
import com.task.employee.util.HobbiesConvertor;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class EmployeeDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Birthdate must be in the YYYY-MM-dd format")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private String birthday;
    private List<String> hobbies;

    public Employee toEntity() {
        try {
            HobbiesConvertor hobbiesConvertor = new HobbiesConvertor();
            return new Employee(UUID.randomUUID(), firstName, lastName, email, LocalDate.parse(birthday), hobbiesConvertor.fromDtoToEntity(hobbies));
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException("Invalid date format. Use the YYYY-MM-dd format for the birthday.");
        }
    }

    public EmployeeEvent toEvent() {
        return new EmployeeEvent(id, firstName, lastName, email, birthday, hobbies);
    }
}
