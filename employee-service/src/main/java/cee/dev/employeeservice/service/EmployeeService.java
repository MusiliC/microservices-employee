package cee.dev.employeeservice.service;

import java.util.List;

import cee.dev.employeeservice.model.EmployeeRequestDto;
import cee.dev.employeeservice.model.EmployeeResponseDto;

public interface EmployeeService {
    EmployeeResponseDto createEmployee(EmployeeRequestDto employeeRequestDto);

    List<EmployeeResponseDto> listAllEmployees();

    EmployeeResponseDto getOneEmployeeByEmpNo(Integer empNo);

    String deleteEmployee(Integer empNo);
}
