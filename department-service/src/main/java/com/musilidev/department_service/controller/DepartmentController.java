package com.musilidev.department_service.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musilidev.department_service.model.DepartmentResponseDto;
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
    public DepartmentResponseDto createDepartment(@RequestBody DepartmentRequestDto departmentRequestDto) {
       return departmentService.createDepartment(departmentRequestDto);
    }

    @PostMapping("batch")
    public List<DepartmentResponseDto> getDepartmentsByIds(@RequestBody List<Integer> ids) {
        return departmentService.getDepartmentsByIds(ids);
    }

    @GetMapping
    List<DepartmentResponseDto> getAllDepartments() {
        return  departmentService.getAllDepartments();
    }

    @GetMapping("{departmentNo}")
    DepartmentResponseDto getDepartmentByDepartmentNumber(@PathVariable Integer departmentNo) {
        return  departmentService.getDepartmentByDepartmentNumber(departmentNo);
    }


    @PutMapping("{departmentNo}")
    DepartmentResponseDto updateDepartment(@PathVariable Integer departmentNo, @RequestBody DepartmentRequestDto departmentRequestDto) {
        return  departmentService.updateDepartment(departmentNo, departmentRequestDto);
    }

    @DeleteMapping("{departmentNo}")
    public Map<String, Object> deleteDepartment(@PathVariable Integer departmentNo) {
        return  departmentService.deleteDepartment(departmentNo);
    }
}
