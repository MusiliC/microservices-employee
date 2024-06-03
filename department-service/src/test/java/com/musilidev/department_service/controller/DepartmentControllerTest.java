package com.musilidev.department_service.controller;

import com.musilidev.department_service.AbstractTestContainersTest;
import com.musilidev.department_service.model.DepartmentCreateResponse;
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
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


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
        ResponseEntity<DepartmentCreateResponse> response = testRestTemplate.exchange(
                API_BASE_URL,
                HttpMethod.POST,
                new HttpEntity<>(deptRequest),
                new ParameterizedTypeReference<DepartmentCreateResponse>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        //make a call to list the data
        ResponseEntity<List<DepartmentCreateResponse>> listResponse = testRestTemplate.exchange(
                API_BASE_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<DepartmentCreateResponse>>() {
                }
        );

        assertThat(listResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Objects.requireNonNull(listResponse.getBody());
        DepartmentCreateResponse departmentCreateResponse = listResponse
                .getBody()
                .stream()
                .filter(deptResponse -> deptResponse.getDepartmentName().equals(deptRequest.getDepartmentName()))
                .findFirst()
                .orElseThrow();

        assertThat(departmentCreateResponse.getDepartmentNumber()).isNotNull();
        assertThat(departmentCreateResponse.getDepartmentName()).isEqualTo(deptRequest.getDepartmentName());

    }

    @Test
    void updateDepartment() {

        //given
        DepartmentRequestDto deptRequest = DepartmentRequestDto
                .builder()
                .departmentName("Science")
                .build();

        //when
        ResponseEntity<DepartmentCreateResponse> response = testRestTemplate.exchange(
                API_BASE_URL,
                HttpMethod.POST,
                new HttpEntity<>(deptRequest),
                new ParameterizedTypeReference<DepartmentCreateResponse>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DepartmentCreateResponse deptRes = Objects.requireNonNull(response.getBody());

        //call to update end point
        ResponseEntity<DepartmentCreateResponse> updateRes = testRestTemplate.exchange(
                API_BASE_URL + "/" + deptRes.getDepartmentNumber(),
                HttpMethod.PUT,
                new HttpEntity<>(DepartmentRequestDto.builder().departmentName("Social").build()),
                new ParameterizedTypeReference<DepartmentCreateResponse>() {
                });
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        DepartmentCreateResponse updatedDeptRes = Objects.requireNonNull(updateRes.getBody());
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
        ResponseEntity<DepartmentCreateResponse> response = testRestTemplate.exchange(
                API_BASE_URL,
                HttpMethod.POST,
                new HttpEntity<>(deptRequest),
                new ParameterizedTypeReference<DepartmentCreateResponse>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DepartmentCreateResponse deptRes = Objects.requireNonNull(response.getBody());

        //call to get end point
        ResponseEntity<DepartmentCreateResponse> getByDeptId = testRestTemplate.exchange(
                API_BASE_URL + "/" + deptRes.getDepartmentNumber(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<DepartmentCreateResponse>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        DepartmentCreateResponse deptResBodyByNo = Objects.requireNonNull(getByDeptId.getBody());
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
        ResponseEntity<DepartmentCreateResponse> response = testRestTemplate.exchange(
                API_BASE_URL,
                HttpMethod.POST,
                new HttpEntity<>(deptRequest),
                new ParameterizedTypeReference<DepartmentCreateResponse>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DepartmentCreateResponse deptRes = response.getBody();
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