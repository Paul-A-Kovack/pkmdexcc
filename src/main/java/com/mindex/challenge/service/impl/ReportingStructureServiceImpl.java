package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation for retrieving {@link ReportingStructure} data entities.
 */
@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    private final EmployeeService employeeService;

    @Autowired
    public ReportingStructureServiceImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReportingStructure read(String employeeId) {
        LOG.debug("Finding ReportingStructure for employee with id [{}]", employeeId);

        Employee employee = employeeService.read(employeeId);

        ReportingStructure reportingStructure = new ReportingStructure();
        reportingStructure.setEmployee(employee);
        reportingStructure.setNumberOfReports(calculateNumberOfReports(employee));

        return reportingStructure;
    }

    /**
     * Calculate the number of employees that report to an employee, either directly or indirectly.
     *
     * @param employee employee to calculate the number of reports for.
     * @return the number of reporting employees for a given employee.
     */
    private int calculateNumberOfReports(Employee employee) {
        int numberOfReports = 0;
        int numberOfDirectReports = Optional.ofNullable(employee.getDirectReports()).map(List::size).orElse(0);
        if (numberOfDirectReports > 0) {
            numberOfReports += numberOfDirectReports;
            for (Employee reportingEmployee : employee.getDirectReports()) {
                reportingEmployee = employeeService.read(reportingEmployee.getEmployeeId());
                numberOfReports += calculateNumberOfReports(reportingEmployee);
            }
        }
        return numberOfReports;
    }
}
