package com.musilidev.department_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentCreateResponse {

    private Integer departmentNumber;

    private String departmentName;

}
