package cee.dev.employeeservice.entity;


import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_employees")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Employee {

    @Id
    @Column(name = "emp_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeNumber;

    @Column(name = "birth_date")
    @Past(message = "birth_date need to be past")
    private  LocalDate birthDate;


    @Column(name = "hire_date")
    @PastOrPresent(message = "hire date need to be past or present")
    private  LocalDate hireDate;

    @Column(name = "first_name")
    @NotEmpty
    private String firstName;


    @Column(name = "last_name")
    @NotEmpty
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "dept_id")
    @NotNull
    private Integer departmentId;


}
