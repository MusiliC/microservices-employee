package cee.dev.employeeservice.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cee.dev.employeeservice.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query("SELECT e from Employee e where e.firstName = :firstName")
    Optional<Employee> findByFirstName(String firstName);
}
