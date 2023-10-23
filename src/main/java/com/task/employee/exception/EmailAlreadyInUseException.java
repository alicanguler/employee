package com.task.employee.exception;

public class EmailAlreadyInUseException extends RuntimeException{
    public EmailAlreadyInUseException(String message){
        super(message);
    }
}
