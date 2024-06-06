package com.musilidev.department_service.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.musilidev.department_service.entity.Department;
import com.musilidev.department_service.model.DepartmentResponseDto;
import com.musilidev.department_service.model.DepartmentRequestDto;
import com.musilidev.department_service.repository.DepartmentRepository;
import com.musilidev.department_service.service.DepartmentService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final ModelMapper modelMapper;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, ModelMapper modelMapper) {
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DepartmentResponseDto createDepartment(DepartmentRequestDto departmentRequestDto) {
        Department deptRequest = modelMapper.map(departmentRequestDto, Department.class);
        Department savedDept = departmentRepository.save(deptRequest);
        return modelMapper.map(savedDept, DepartmentResponseDto.class);
    }

    @Override
    public List<DepartmentResponseDto> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(department -> modelMapper.map(department, DepartmentResponseDto.class))
                .toList();
    }

    @Override
    public DepartmentResponseDto getDepartmentByDepartmentNumber(Integer departmentNo) {
        Department departmentFound = departmentRepository
                .findById(departmentNo).orElseThrow(() -> new RuntimeException("Department not found"));

        return modelMapper.map(departmentFound, DepartmentResponseDto.class);
    }

    @Override
    public DepartmentResponseDto updateDepartment(Integer departmentNo, DepartmentRequestDto departmentRequestDto) {
        //find the dept -> check if dept name exists

        Department department = departmentRepository
                .findById(departmentNo).orElseThrow(() -> new RuntimeException("Department not found"));

        boolean deptNameExist = departmentRepository.findByDepartmentName(departmentRequestDto.getDepartmentName()).isPresent();

        if (deptNameExist) {
            throw new RuntimeException("Department name " + departmentRequestDto.getDepartmentName() + " already exist");
        }
        department.setDepartmentName(departmentRequestDto.getDepartmentName());
        //update field if the name does not exist
        Department updatedDepartment = departmentRepository.save(department);
        return modelMapper.map(updatedDepartment, DepartmentResponseDto.class);
    }

    @Override
    public Map<String, Object> deleteDepartment(Integer departmentNo) {

        departmentRepository.deleteById(departmentNo);
        Map<String, Object> response = new HashMap<>();
        response.put("Success", true);
        return response;
    }

    @Override
    public List<DepartmentResponseDto> getDepartmentsByIds(List<Integer> ids) {
        return departmentRepository.findAllById(ids)
                .stream()
                .map(department -> modelMapper.map(department, DepartmentResponseDto.class))
                .toList();
    }
}
