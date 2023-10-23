package com.task.employee.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HobbiesConvertor {
    public String fromDtoToEntity(List<String> hobbies){
        return hobbies == null ? null : hobbies.stream().map(String::trim).collect(Collectors.joining(","));
    }
    public List<String> fromEntityToDto(String hobbies){
        return hobbies == null ? null :Arrays.stream(hobbies.split(",")).map(String::trim).collect(Collectors.toList());
    }
}
