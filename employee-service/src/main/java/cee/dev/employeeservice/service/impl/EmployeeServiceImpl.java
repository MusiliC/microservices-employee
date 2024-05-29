package cee.dev.employeeservice.service.impl;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import cee.dev.employeeservice.entity.Employee;
import cee.dev.employeeservice.model.EmployeeRequestDto;
import cee.dev.employeeservice.model.EmployeeResponseDto;
import cee.dev.employeeservice.repository.EmployeeRepository;
import cee.dev.employeeservice.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public EmployeeResponseDto createEmployee(EmployeeRequestDto employeeRequestDto) {
        Employee empReq = modelMapper.map(employeeRequestDto, Employee.class);
        var savedEmployee = employeeRepository.save(empReq);
        return modelMapper.map(savedEmployee, EmployeeResponseDto.class);

    }

    @Override
    public List<EmployeeResponseDto> listAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(e -> modelMapper.map(e, EmployeeResponseDto.class))
                .toList();
    }

    @Override
    public EmployeeResponseDto getOneEmployeeByEmpNo(Integer empNo) {
        return employeeRepository.findById(empNo)
                .map(e -> modelMapper.map(e, EmployeeResponseDto.class))
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    @Override
    public String deleteEmployee(Integer empNo) {
        employeeRepository.deleteById(empNo);
        return "Employee deleted";
    }
}
