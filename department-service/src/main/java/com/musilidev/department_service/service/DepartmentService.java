package com.musilidev.department_service.service;

import com.musilidev.department_service.model.DepartmentResponseDto;
import com.musilidev.department_service.model.DepartmentRequestDto;
import java.util.List;
import java.util.Map;

public interface DepartmentService {

    DepartmentResponseDto createDepartment(DepartmentRequestDto departmentRequestDto);

    List<DepartmentResponseDto> getAllDepartments();

    DepartmentResponseDto getDepartmentByDepartmentNumber(Integer departmentNo);

    DepartmentResponseDto updateDepartment(Integer departmentNo, DepartmentRequestDto departmentRequestDto);

    Map<String, Object> deleteDepartment(Integer departmentNo);

    List<DepartmentResponseDto> getDepartmentsByIds(List<Integer> ids);
}
