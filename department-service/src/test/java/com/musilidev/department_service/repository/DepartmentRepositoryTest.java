package com.musilidev.department_service.repository;


import com.musilidev.department_service.AbstractTestContainersTest;
import com.musilidev.department_service.entity.Department;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DepartmentRepositoryTest extends AbstractTestContainersTest {


    @Autowired
    private DepartmentRepository departmentRepository;

    @BeforeEach
    void setUp() {
        Department computerScience = Department.builder().departmentName("Computer Science").build();
        departmentRepository.save(computerScience);
    }

    @AfterEach
    void tearDown() {
        departmentRepository.deleteAll();
    }

    @Test
    public void ShouldFindByDepartmentName() {
        //given
        //when
        Optional<Department> computerScience = departmentRepository.findByDepartmentName("Computer Science");
        //then
        assertThat(computerScience).isPresent();
    }

    @Test
    public void ShouldNotFindByDepartmentName() {
        //given
        //when
        Optional<Department> computerScience = departmentRepository.findByDepartmentName("Information Technology");
        //then
        assertThat(computerScience).isNotPresent();
    }

}