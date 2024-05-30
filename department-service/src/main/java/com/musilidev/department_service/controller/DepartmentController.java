package com.musilidev.department_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musilidev.department_service.model.DepartmentCreateResponse;
import com.musilidev.department_service.model.DepartmentRequestDto;
import com.musilidev.department_service.service.DepartmentService;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/departments")
public class DepartmentController {
    
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService){
        this.departmentService = departmentService;
    }

    @PostMapping
    public DepartmentCreateResponse createDepartment(@RequestBody DepartmentRequestDto departmentRequestDto) {
       return departmentService.createDepartment(departmentRequestDto);
    }

    @GetMapping
    List<DepartmentCreateResponse> getAllDepartments() {
        return  departmentService.getAllDepartments();
    }

    @GetMapping("{departmentNo}")
    DepartmentCreateResponse getDepartment(@PathVariable Integer departmentNo) {
        return  departmentService.singleDepartment(departmentNo);
    }


    @PutMapping("{departmentNo}")
    DepartmentCreateResponse updateDepartment(@PathVariable Integer departmentNo, @RequestBody DepartmentRequestDto departmentRequestDto) {
        return  departmentService.updateDepartment(departmentNo, departmentRequestDto);
    }

    @PutMapping("{departmentNo}")
    public Map<String, Object> deleteDepartment(@PathVariable Integer departmentNo) {
        return  departmentService.deleteDepartment(departmentNo);
    }
}
