package cee.dev.employeeservice.repository;

import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;
import cee.dev.employeeservice.entity.Employee;
import cee.dev.employeeservice.entity.Gender;



@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        Employee testEmployee = Employee
                .builder()
                .firstName("Musili")
                .lastName("Null")
                .gender(Gender.MALE)
                .birthDate(LocalDate.of(2000, 1, 1))
                .hireDate(LocalDate.of(2022, 4, 4))
                .departmentId(1)
                .build();

        employeeRepository.save(testEmployee);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void testFindByFirstNameReturnsOptionalEmployee() {
        //when

        //given
        Optional<Employee> employee = employeeRepository.findByFirstName("Musili");

        //assert
        assertTrue(employee.isPresent());
    }
}