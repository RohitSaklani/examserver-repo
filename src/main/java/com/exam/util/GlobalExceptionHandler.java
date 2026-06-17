package com.exam.util;
import com.exam.Exception.UserAlreadyExistException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String,String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();

            errors.put(fieldName, message);
        });


        return new ResponseEntity<>(  new ErrorResponse( errors,"Invalid Data")
                , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleUserExist(UserAlreadyExistException e){
       ErrorResponse error = new ErrorResponse();
        Map map = new HashMap<>();
        map.put("error",e.getMessage());
        error.setMessage("User already exist");
        error.setErrors(map);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleLoginException(BadCredentialsException e){
        System.out.println("entered custom bad crenrtials exception ");
        ErrorResponse error  = new ErrorResponse();
        Map map = new HashMap<>();
        map.put("error",e.getMessage());
        error.setMessage("Wrong password");
        error.setErrors(map);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }


    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorResponse> handleSqlError(SQLException e){
        e.printStackTrace();
        System.out.println("internal sql error");
        ErrorResponse error  = new ErrorResponse();
        Map map = new HashMap<>();
        map.put("error","Something went wrong");
        error.setMessage("Internal Server Error");
        error.setErrors(map);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleAllRuntimeError(RuntimeException e){
        e.printStackTrace();
        System.out.println("runtime exception handler running insdie controller advice");
        ErrorResponse error  = new ErrorResponse();
        Map map = new HashMap<>();
        map.put("error",e.getMessage());
        error.setMessage("Internal Server Error");
        error.setErrors(map);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }



}
