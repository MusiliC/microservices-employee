package com.musilidev.department_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musilidev.department_service.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer>{

    boolean findByDepartmentName(String departmentName);
}
