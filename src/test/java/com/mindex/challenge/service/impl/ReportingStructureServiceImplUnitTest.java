package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link ReportingStructureServiceImpl}.
 */
@ExtendWith(MockitoExtension.class)
class ReportingStructureServiceImplUnitTest {

    @Mock
    private EmployeeService mockEmployeeService;

    @InjectMocks
    private ReportingStructureServiceImpl service;

    @Test
    void read_GivenEmployeeId_WhenEmployeeHasNullDirectReports_ReturnsExpected() {
        String employeeId = UUID.randomUUID().toString();
        Employee employee = buildEmployee(employeeId, null);

        when(mockEmployeeService.read(employeeId)).thenReturn(employee);

        ReportingStructure actual = service.read(employeeId);

        verify(mockEmployeeService, only()).read(employeeId);

        ReportingStructure expected = new ReportingStructure();
        expected.setEmployee(employee);
        expected.setNumberOfReports(0);

        assertEquals(expected, actual);
    }

    @Test
    void read_GivenEmployeeId_WhenEmployeeHasEmptyListOfDirectReports_ReturnsExpected() {
        String employeeId = UUID.randomUUID().toString();
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        employee.setDirectReports(new ArrayList<>());

        when(mockEmployeeService.read(employeeId)).thenReturn(employee);

        ReportingStructure actual = service.read(employeeId);

        verify(mockEmployeeService, only()).read(employeeId);

        ReportingStructure expected = new ReportingStructure();
        expected.setEmployee(employee);
        expected.setNumberOfReports(0);

        assertEquals(expected, actual);
    }

    @Test
    void read_GivenEmployeeId_WhenEmployeeHasOnlyDirectReports_ReturnsExpected() {
        String employeeId = UUID.randomUUID().toString();
        String directReportEmployeeId = UUID.randomUUID().toString();

        Employee directReportEmployee = buildEmployee(directReportEmployeeId, null);

        Employee employee = buildEmployee(employeeId, Collections.singletonList(directReportEmployee));

        when(mockEmployeeService.read(employeeId)).thenReturn(employee);
        when(mockEmployeeService.read(directReportEmployeeId)).thenReturn(directReportEmployee);

        ReportingStructure actual = service.read(employeeId);

        verify(mockEmployeeService, times(1)).read(employeeId);
        verify(mockEmployeeService, times(1)).read(directReportEmployeeId);
        verifyNoMoreInteractions(mockEmployeeService);

        ReportingStructure expected = new ReportingStructure();
        expected.setEmployee(employee);
        expected.setNumberOfReports(1);

        assertEquals(expected, actual);
    }

    @Test
    void read_GivenEmployeeId_WhenEmployeeHasDirectAndIndirectReports_ReturnsExpected() {
        String seniorEmployeeId = UUID.randomUUID().toString();
        String midEmployeeId = UUID.randomUUID().toString();
        String juniorEmployeeId1 = UUID.randomUUID().toString();
        String juniorEmployeeId2 = UUID.randomUUID().toString();

        Employee juniorEmployee1 = buildEmployee(juniorEmployeeId1, null);
        Employee juniorEmployee2 = buildEmployee(juniorEmployeeId2, null);
        Employee midEmployee = buildEmployee(midEmployeeId, Arrays.asList(juniorEmployee1, juniorEmployee2));
        Employee seniorEmployee = buildEmployee(seniorEmployeeId, Collections.singletonList(midEmployee));

        when(mockEmployeeService.read(seniorEmployeeId)).thenReturn(seniorEmployee);
        when(mockEmployeeService.read(midEmployeeId)).thenReturn(midEmployee);
        when(mockEmployeeService.read(juniorEmployeeId1)).thenReturn(juniorEmployee1);
        when(mockEmployeeService.read(juniorEmployeeId2)).thenReturn(juniorEmployee2);

        ReportingStructure actual = service.read(seniorEmployeeId);

        verify(mockEmployeeService, times(4)).read(anyString());
        verifyNoMoreInteractions(mockEmployeeService);

        ReportingStructure expected = new ReportingStructure();
        expected.setEmployee(seniorEmployee);
        expected.setNumberOfReports(3);

        assertEquals(expected, actual);
    }

    private Employee buildEmployee(String employeeId, List<Employee> reportingEmployees) {
        Employee directReportEmployee = new Employee();
        directReportEmployee.setEmployeeId(employeeId);
        directReportEmployee.setDirectReports(reportingEmployees);
        return directReportEmployee;
    }

}