package com.musilidev.department_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_departments", indexes = {
        @Index(columnList = "dept_name", name = "IDX_TBL_DEPARTMENTS_DEPT_NAME")
}, uniqueConstraints = {
        @UniqueConstraint(columnNames = "dept_name", name = "IDX_TBL_DEPARTMENTS_DEPT_NAME")

})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Department {

    @Id
    @Column(name = "dept_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer departmentNumber;

    @Column(name = "dept_name")
    @NotEmpty
    @NotBlank
    private String departmentName;
}
