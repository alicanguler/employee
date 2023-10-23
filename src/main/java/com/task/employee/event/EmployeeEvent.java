package com.task.employee.event;

import lombok.*;

import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class EmployeeEvent {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String birthday;
    private List<String> hobbies;
}
