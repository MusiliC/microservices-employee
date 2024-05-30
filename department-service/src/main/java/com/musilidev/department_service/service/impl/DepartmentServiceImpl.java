package com.musilidev.department_service.service.impl;

import org.springframework.stereotype.Service;

import com.musilidev.department_service.repository.DepartmentRepository;
import com.musilidev.department_service.service.DepartmentService;


@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository){
        this.departmentRepository = departmentRepository;
    }
    
}
