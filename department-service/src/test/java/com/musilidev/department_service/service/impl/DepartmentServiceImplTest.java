package com.musilidev.department_service.service.impl;

import com.musilidev.department_service.entity.Department;
import com.musilidev.department_service.model.DepartmentResponseDto;
import com.musilidev.department_service.model.DepartmentRequestDto;
import com.musilidev.department_service.repository.DepartmentRepository;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {

        @Mock
        public DepartmentRepository departmentRepository;

        public ModelMapper modelMapper = new ModelMapper();

        private DepartmentServiceImpl departmentServiceImpl;

        @BeforeEach
        void setUp() {
                departmentServiceImpl = new DepartmentServiceImpl(departmentRepository, modelMapper);
        }

        @AfterEach
        void tearDown() {
        }

        @Test
        void testThatCreateDepartment() {

                // given
                DepartmentRequestDto computerScience = DepartmentRequestDto.builder().departmentName("Computer science")
                                .build();
                // when
                when(departmentRepository.save(any(Department.class))).thenReturn(Department
                                .builder()
                                .departmentName("Computer science")
                                .departmentNumber(1)
                                .build());
                DepartmentResponseDto actualDepartmentResponse = departmentServiceImpl
                                .createDepartment(computerScience);
                // then
                DepartmentResponseDto expectedResponse = DepartmentResponseDto
                                .builder()
                                .departmentName("Computer science")
                                .departmentNumber(1)
                                .build();
                assertThat(actualDepartmentResponse.getDepartmentName()).isNotNull();
                assertThat(actualDepartmentResponse.getDepartmentName())
                                .isEqualTo(expectedResponse.getDepartmentName());
                assertThat(actualDepartmentResponse.getDepartmentNumber()).isEqualTo(1);
        }

        @Test
        void testThatGetAllDepartments() {
                // given
                departmentServiceImpl.getAllDepartments();
                // when
                // then
                verify(departmentRepository).findAll();
        }

        @Test
        void testThatGetByDeptNoShouldHaveValueWhenFound() {
                // given
                Integer deptNo = 1;
                Department computerScience = Department
                                .builder()
                                .departmentName("Computer science")
                                .departmentNumber(1)
                                .build();
                when(departmentRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(computerScience));
                // when
                DepartmentResponseDto departmentResponseDto = departmentServiceImpl.getDepartmentByDepartmentNumber(deptNo);
                // then
                assertThat(departmentResponseDto.getDepartmentNumber()).isEqualTo(deptNo);
                assertThat(departmentResponseDto.getDepartmentName()).isEqualTo(computerScience.getDepartmentName());
        }

        @Test
        void testThatGetByDeptNoShouldThrowRunTimeExceptionWhenNotFound() {
                // given
                Integer deptNo = 1;
                Department computerScience = Department
                                .builder()
                                .departmentName("Computer science")
                                .departmentNumber(1)
                                .build();
                when(departmentRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
                // when

                assertThatThrownBy(() -> departmentServiceImpl.getDepartmentByDepartmentNumber(deptNo))
                                .isInstanceOf(RuntimeException.class)
                                .hasMessage("Department not found");

        }

        @Test
        void testThatUpdateDepartment() {
                // given
                Integer deptNo = 1;

                DepartmentRequestDto deptRequest = DepartmentRequestDto.builder().departmentName("IT").build();

                Department dept = Department
                                .builder()
                                .departmentName("Computer science")
                                .departmentNumber(1)
                                .build();
                when(departmentRepository.findById(eq(deptNo))).thenReturn(Optional.of(dept));
                when(departmentRepository.findByDepartmentName(eq(deptRequest.getDepartmentName())))
                                .thenReturn(Optional.empty()); // simulates new name is not taken

                when(departmentRepository.save(any(Department.class))).thenReturn(Department
                                .builder()
                                .departmentName("IT")
                                .departmentNumber(1)
                                .build());

                // when
                DepartmentResponseDto updateDepartment = departmentServiceImpl.updateDepartment(deptNo, deptRequest);

                assertThat(updateDepartment.getDepartmentName()).isEqualTo(deptRequest.getDepartmentName());
        }

        @Test
        void testThatUpdateDepartmentWhenNameExists() {
                // given
                Integer deptNo = 1;

                DepartmentRequestDto deptRequest = DepartmentRequestDto.builder().departmentName("IT").build();

                Department dept = Department
                                .builder()
                                .departmentName("IT")
                                .departmentNumber(1)
                                .build();

                when(departmentRepository.findById(eq(deptNo))).thenReturn(Optional.of(dept));
                when(departmentRepository.findByDepartmentName(eq(deptRequest.getDepartmentName())))
                                .thenReturn(Optional.of(dept)); // simulates new name is not taken

                // when
                assertThatThrownBy(() -> departmentServiceImpl.updateDepartment(deptNo, deptRequest))
                                .isInstanceOf(RuntimeException.class)
                                .hasMessage("Department name " + deptRequest.getDepartmentName() + " already exist");
                verify(departmentRepository, never()).save(any());
        }

        @Test
        void testThatUpdateDepartmentWhenDeptNotExist() {
                // given
                Integer deptNo = 1;

                DepartmentRequestDto deptRequest = DepartmentRequestDto.builder().departmentName("IT").build();

                Department dept = Department
                                .builder()
                                .departmentName("IT")
                                .departmentNumber(1)
                                .build();

                when(departmentRepository.findById(eq(deptNo))).thenReturn(Optional.empty());              

                // when
                assertThatThrownBy(() -> departmentServiceImpl.updateDepartment(deptNo, deptRequest))
                                .isInstanceOf(RuntimeException.class)
                                .hasMessage("Department not found");
                verify(departmentRepository, never()).save(any());
        }

        @Test
        void testThatDeleteDepartment() {
                // given
                Integer deptNo = 1;

                Map<String, Object> deleteDepartment = departmentServiceImpl.deleteDepartment(deptNo);

                verify(departmentRepository).deleteById(deptNo);
                assertThat(deleteDepartment.containsKey("success"));
                assertThat(deleteDepartment.containsValue(true));

        }
}