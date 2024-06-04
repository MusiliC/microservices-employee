package com.musilidev.department_service.controller;

import com.musilidev.department_service.AbstractTestContainersTest;
import com.musilidev.department_service.model.DepartmentResponseDto;
import com.musilidev.department_service.model.DepartmentRequestDto;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class DepartmentControllerTest extends AbstractTestContainersTest {


    private static final String API_BASE_URL = "/api/departments";

    @Autowired
    TestRestTemplate testRestTemplate;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createDepartment() {
        //given

        DepartmentRequestDto deptRequest = DepartmentRequestDto
                .builder()
                .departmentName("Math")
                .build();

        //when
        ResponseEntity<DepartmentResponseDto> response = testRestTemplate.exchange(
                API_BASE_URL,
                HttpMethod.POST,
                new HttpEntity<>(deptRequest),
                new ParameterizedTypeReference<DepartmentResponseDto>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        //make a call to list the data
        ResponseEntity<List<DepartmentResponseDto>> listResponse = testRestTemplate.exchange(
                API_BASE_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<DepartmentResponseDto>>() {
                }
        );

        assertThat(listResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Objects.requireNonNull(listResponse.getBody());
        DepartmentResponseDto departmentResponseDto = listResponse
                .getBody()
                .stream()
                .filter(deptResponse -> deptResponse.getDepartmentName().equals(deptRequest.getDepartmentName()))
                .findFirst()
                .orElseThrow();

        assertThat(departmentResponseDto.getDepartmentNumber()).isNotNull();
        assertThat(departmentResponseDto.getDepartmentName()).isEqualTo(deptRequest.getDepartmentName());

    }

    @Test
    void updateDepartment() {

        //given
        DepartmentRequestDto deptRequest = DepartmentRequestDto
                .builder()
                .departmentName("Science")
                .build();

        //when
        ResponseEntity<DepartmentResponseDto> response = testRestTemplate.exchange(
                API_BASE_URL,
                HttpMethod.POST,
                new HttpEntity<>(deptRequest),
                new ParameterizedTypeReference<DepartmentResponseDto>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DepartmentResponseDto deptRes = Objects.requireNonNull(response.getBody());

        //call to update end point
        ResponseEntity<DepartmentResponseDto> updateRes = testRestTemplate.exchange(
                API_BASE_URL + "/" + deptRes.getDepartmentNumber(),
                HttpMethod.PUT,
                new HttpEntity<>(DepartmentRequestDto.builder().departmentName("Social").build()),
                new ParameterizedTypeReference<DepartmentResponseDto>() {
                });
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        DepartmentResponseDto updatedDeptRes = Objects.requireNonNull(updateRes.getBody());
        assertThat(updatedDeptRes.getDepartmentName()).isEqualTo("Social");
        assertThat(updatedDeptRes.getDepartmentNumber()).isEqualTo(deptRes.getDepartmentNumber());
    }

    @Test
    void getDepartment() {

        //given
        DepartmentRequestDto deptRequest = DepartmentRequestDto
                .builder()
                .departmentName("Human Resource")
                .build();

        //when
        ResponseEntity<DepartmentResponseDto> response = testRestTemplate.exchange(
                API_BASE_URL,
                HttpMethod.POST,
                new HttpEntity<>(deptRequest),
                new ParameterizedTypeReference<DepartmentResponseDto>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DepartmentResponseDto deptRes = Objects.requireNonNull(response.getBody());

        //call to get end point
        ResponseEntity<DepartmentResponseDto> getByDeptId = testRestTemplate.exchange(
                API_BASE_URL + "/" + deptRes.getDepartmentNumber(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<DepartmentResponseDto>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        DepartmentResponseDto deptResBodyByNo = Objects.requireNonNull(getByDeptId.getBody());
        assertThat(deptResBodyByNo.getDepartmentName()).isEqualTo(deptRequest.getDepartmentName());

    }

    @Test
    void deleteDepartment() {

        //given

        DepartmentRequestDto deptRequest = DepartmentRequestDto
                .builder()
                .departmentName("Applied")
                .build();

        //when
        ResponseEntity<DepartmentResponseDto> response = testRestTemplate.exchange(
                API_BASE_URL,
                HttpMethod.POST,
                new HttpEntity<>(deptRequest),
                new ParameterizedTypeReference<DepartmentResponseDto>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DepartmentResponseDto deptRes = response.getBody();
        Objects.requireNonNull(deptRes);

        //call to delete end point
        ResponseEntity<Map<String, Object>> deleteRes = testRestTemplate.exchange(
                API_BASE_URL + "/" + deptRes.getDepartmentNumber(),
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {
                });

        Map<String, Object> delResMap = Objects.requireNonNull(deleteRes.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(delResMap.containsKey("success"));
        assertThat(delResMap.containsValue(true));
    }
}