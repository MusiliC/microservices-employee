package cee.dev.employeeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cee.dev.employeeservice.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
