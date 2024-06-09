package cee.dev.employeeservice.service.impl;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

import cee.dev.employeeservice.entity.Employee;
import cee.dev.employeeservice.model.DepartmentResponseDto;
import cee.dev.employeeservice.model.EmployeeCreateRequestDto;
import cee.dev.employeeservice.model.EmployeeResponseDto;
import cee.dev.employeeservice.model.EmployeeWithDepartment;
import cee.dev.employeeservice.repository.EmployeeRepository;
import cee.dev.employeeservice.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

@Value("${department-service-host-ur}")
private String departmentServiceHostUrl;

    private final EmployeeRepository employeeRepository;

    private final WebClient webClient;

    private final RestClient restClient;

    private final ModelMapper modelMapper;

    private Map<Integer, DepartmentResponseDto> collect;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, WebClient webClient, RestClient restClient,
            ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.webClient = webClient;
        this.restClient = restClient;
        this.modelMapper = modelMapper;
    }

    @Override
    public EmployeeResponseDto createEmployee(EmployeeCreateRequestDto employeeCreateRequestDto) {
        Employee empReq = modelMapper.map(employeeCreateRequestDto, Employee.class);
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

    @Override
    public List<EmployeeWithDepartment> getEmployeesWithDepartment() {

        List<EmployeeWithDepartment> employeesList = employeeRepository.findAll()
                .stream()
                .map(e -> modelMapper.map(e, EmployeeWithDepartment.class))
                .toList();

        // ?solution 1
        // log.info("Solution 1");
        // setDepartmentInformationByIndividualCalls(employeesList);

        // ?solution 2
        log.info("Solution 2");
        List<Integer> departmentIds = employeesList.stream().map(emp -> emp.getDepartmentId()).toList();
        // List<Integer> departmentIds =
        // employeesList.stream().map(EmployeeWithDepartment::getDepartmentId).toList();

        // http://localhost:8081/api/departments/batch
        // [ids]

        return getEmployeesBySendingBatchIdRequest(employeesList, departmentIds);
    }

    private List<EmployeeWithDepartment> getEmployeesBySendingBatchIdRequest(List<EmployeeWithDepartment> employeesList,
            List<Integer> departmentIds) {
        List<DepartmentResponseDto> departments = restClient.post()
                .uri(departmentServiceHostUrl + "/api/departments/batch")
                .body(departmentIds)
                .retrieve()
                .body(new ParameterizedTypeReference<List<DepartmentResponseDto>>() {
                });

        if (departments == null) {
            departments = new ArrayList<>();
        }

        Map<Integer, DepartmentResponseDto> departmentMap = departments.stream()
                .collect(Collectors.toMap(DepartmentResponseDto::getDepartmentNumber, Function.identity()));
        return employeesList.stream().map(emp -> {
            emp.setDepartmentResponseDto(departmentMap.get(emp.getDepartmentId()));
            return emp;
        })
                .toList();
    }

    private void setDepartmentInformationByIndividualCalls(List<EmployeeWithDepartment> employeesList) {
        employeesList.stream().forEach(emp -> {
            // DepartmentResponseDto dpt = getDepartmentUsingWebClient(emp);
            DepartmentResponseDto dpt = getDepartmentUsingRestClient(emp);
            emp.setDepartmentResponseDto(dpt);
        });
    }

    private DepartmentResponseDto getDepartmentUsingWebClient(EmployeeWithDepartment emp) {
        log.info("Making call using web client");
        return webClient
                .get()
                .uri(departmentServiceHostUrl + "/api/departments/" + emp.getDepartmentId())
                .retrieve()
                .bodyToMono(DepartmentResponseDto.class)
                .block();
    }

    private DepartmentResponseDto getDepartmentUsingRestClient(EmployeeWithDepartment emp) {
        log.info("Making call using rest client");
        return restClient
                .get()
                .uri(departmentServiceHostUrl + "/api/departments/" + emp.getDepartmentId())
                .retrieve()
                .body(DepartmentResponseDto.class);
    }
}
