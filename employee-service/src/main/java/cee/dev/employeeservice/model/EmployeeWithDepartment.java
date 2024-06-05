package cee.dev.employeeservice.model;


import java.time.LocalDate;

import cee.dev.employeeservice.entity.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeWithDepartment {

    private Integer employeeNumber;

    private LocalDate birthDate;

    private  LocalDate hireDate;

    private String firstName;

    private String lastName;

    private Gender gender;

    private DepartmentResponseDto departmentResponseDto;

    private Integer departmentId;
}
