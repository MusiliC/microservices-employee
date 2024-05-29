package cee.dev.employeeservice.model;

import java.time.LocalDate;

import cee.dev.employeeservice.entity.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeRequestDto {

    @Past(message = "birth_date need to be past")

    private LocalDate birthDate;


    @PastOrPresent(message = "hire date need to be past or present")

    private LocalDate hireDate;


    @NotEmpty
    private String firstName;


    @NotEmpty
    private String lastName;

    @Enumerated(EnumType.STRING)

    private Gender gender;
}
