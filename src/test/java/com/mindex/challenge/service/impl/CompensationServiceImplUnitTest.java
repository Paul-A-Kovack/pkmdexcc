package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link CompensationServiceImpl}.
 */
@ExtendWith(MockitoExtension.class)
class CompensationServiceImplUnitTest {



    @Mock
    private CompensationRepository mockCompensationRepository;

    @InjectMocks
    private CompensationServiceImpl service;

    @Test
    void create_GivenNullCompensation_ThrowsException() {
        try {
            service.create(null);
            fail("IllegalArgumentException was expected.");
        } catch (IllegalArgumentException e) {
            verifyNoInteractions(mockCompensationRepository);
        }
    }

    @Test
    void create_GivenEmptyCompensation_ThrowsException() {
        try {
            service.create(new Compensation());
            fail("IllegalArgumentException was expected.");
        } catch (IllegalArgumentException e) {
            verifyNoInteractions(mockCompensationRepository);
        }
    }

    @Test
    void create_GivenCompensationButNoEmployeeId_ThrowsException() {
        Compensation compensation = new Compensation();
        compensation.setEmployee(new Employee());
        try {
            service.create(compensation);
            fail("IllegalArgumentException was expected.");
        } catch (IllegalArgumentException e) {
            verifyNoInteractions(mockCompensationRepository);
        }
    }

    @Test
    void create_GivenCompensation_ReturnsExpected() {
        String employeeId = UUID.randomUUID().toString();

        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);

        Compensation compensation = new Compensation();
        compensation.setEmployee(employee);
        compensation.setSalary(new BigDecimal("100000.00"));
        compensation.setEffectiveDate(LocalDate.now());

        when(mockCompensationRepository.save(compensation)).thenReturn(compensation);

        Compensation savedCompensation = service.create(compensation);

        verify(mockCompensationRepository, only()).save(compensation);
        assertEquals(compensation, savedCompensation);
    }

    @Test
    void read_WhenCompensationExists_ReturnsExpected() {
        String employeeId = UUID.randomUUID().toString();

        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);

        Compensation compensation = new Compensation();
        compensation.setEmployee(employee);
        compensation.setSalary(new BigDecimal("100000.00"));
        compensation.setEffectiveDate(LocalDate.now());

        when(mockCompensationRepository.findByEmployee_EmployeeId(employeeId))
                .thenReturn(Collections.singletonList(compensation));

        List<Compensation> compensations = service.read(employeeId);

        verify(mockCompensationRepository, only()).findByEmployee_EmployeeId(employeeId);

        assertEquals(compensation, compensations.get(0));
    }

    @Test
    void read_WhenCompensationDoesNotExist_ReturnsExpected() {
        String employeeId = UUID.randomUUID().toString();


        when(mockCompensationRepository.findByEmployee_EmployeeId(employeeId))
                .thenReturn(new ArrayList<>());

        List<Compensation> compensations = service.read(employeeId);

        verify(mockCompensationRepository, only()).findByEmployee_EmployeeId(employeeId);

        assertEquals(0, compensations.size());
    }
}