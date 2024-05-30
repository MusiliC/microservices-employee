package com.musilidev.department_service.service;

import com.musilidev.department_service.model.DepartmentCreateResponse;
import com.musilidev.department_service.model.DepartmentRequestDto;
import java.util.List;
import java.util.Map;

public interface DepartmentService {

    DepartmentCreateResponse createDepartment(DepartmentRequestDto departmentRequestDto);

    List<DepartmentCreateResponse> getAllDepartments();

    DepartmentCreateResponse singleDepartment(Integer departmentNo);

    DepartmentCreateResponse updateDepartment(Integer departmentNo, DepartmentRequestDto departmentRequestDto);

    Map<String, Object> deleteDepartment(Integer departmentNo);
}
