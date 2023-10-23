package com.task.employee.entity;

import com.task.employee.dto.EmployeeDto;
import com.task.employee.util.HobbiesConvertor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name="FIRST_NAME")
    private String firstName;
    @Column(name="LAST_NAME")
    private String lastName;
    @Column(unique=true, name = "EMAIL")
    private String email;
    @Column(name="BIRTHDAY")
    private LocalDate birthday;
    @Column(name="HOBBIES")
    private String hobbies;




    public EmployeeDto toEmployeeDto(){
        HobbiesConvertor hobbiesConvertor = new HobbiesConvertor();
        return new EmployeeDto(id,firstName,lastName,email,birthday.toString(), hobbiesConvertor.fromEntityToDto(hobbies));
    }
}
