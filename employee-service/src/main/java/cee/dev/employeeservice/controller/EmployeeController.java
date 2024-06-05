package cee.dev.employeeservice.controller;


import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cee.dev.employeeservice.model.EmployeeCreateRequestDto;

import cee.dev.employeeservice.model.EmployeeResponseDto;
import cee.dev.employeeservice.model.EmployeeWithDepartment;
import cee.dev.employeeservice.service.EmployeeService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public EmployeeResponseDto create(@RequestBody @Valid EmployeeCreateRequestDto employeeRequestDto){
        return employeeService.createEmployee(employeeRequestDto);
    }

    @GetMapping
    public List<EmployeeResponseDto>  getEmployees(){
        return employeeService.listAllEmployees();
    }

    @GetMapping("with-department")
    public List<EmployeeWithDepartment>  getEmployeesWithDepartment(){
        return employeeService.getEmployeesWithDepartment();
    }

    @GetMapping("{empNo}")
    public EmployeeResponseDto  getOneEmployeeByEmpNo(@PathVariable("empNo") Integer empNo){
        return employeeService.getOneEmployeeByEmpNo(empNo);
    }

    @DeleteMapping("{empNo}")
    public String  deleteEmployeeByEmpNo(@PathVariable("empNo") Integer empNo){
        return employeeService.deleteEmployee(empNo);
    }
}
