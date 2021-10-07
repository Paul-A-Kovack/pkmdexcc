package com.mindex.challenge.service;

import com.mindex.challenge.data.ReportingStructure;

/**
 * Service for retrieving {@link ReportingStructure} data entities.
 */
public interface ReportingStructureService {

    /**
     * Retrieve the {@link ReportingStructure} for a specific employee.
     *
     * @param employeeId the employee id.
     * @return the {@link ReportingStructure} for that employee.
     */
    ReportingStructure read(String employeeId);
}
