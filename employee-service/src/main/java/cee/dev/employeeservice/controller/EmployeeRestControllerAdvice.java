package cee.dev.employeeservice.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EmployeeRestControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleRuntimeException(RuntimeException ex) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", false);
        map.put("message", ex.getMessage());
        return map;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleException(Exception ex) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", false);
        map.put("message", ex.getMessage());
        return map;
    }

}
