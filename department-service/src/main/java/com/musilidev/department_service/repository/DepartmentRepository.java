package com.musilidev.department_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musilidev.department_service.entity.Department;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Integer>{

    Optional<Department> findByDepartmentName(String departmentName);
}
