package cee.dev.employeeservice.service;

import java.util.List;

import cee.dev.employeeservice.model.EmployeeCreateRequestDto;
import cee.dev.employeeservice.model.EmployeeResponseDto;
import cee.dev.employeeservice.model.EmployeeWithDepartment;

public interface EmployeeService {
    EmployeeResponseDto createEmployee(EmployeeCreateRequestDto employeeCreateRequestDto);

    List<EmployeeResponseDto> listAllEmployees();

    EmployeeResponseDto getOneEmployeeByEmpNo(Integer empNo);

    String deleteEmployee(Integer empNo);

    List<EmployeeWithDepartment> getEmployeesWithDepartment();
}
