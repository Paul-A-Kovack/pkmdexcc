package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;

import java.util.List;

/**
 * Service for creating and retrieving {@link Compensation} data entities.
 */
public interface CompensationService {

    /**
     * Create new {@link Compensation} for an employee.
     *
     * @param compensation compensation to be created.
     * @return the created compensation entity.
     */
    Compensation create(Compensation compensation);

    /**
     * Retrieve all {@link Compensation} entities for an employee.
     *
     * @param employeeId the employee to retrieve compensation for.
     * @return list of all compensation entities for an employee.
     */
    List<Compensation> read(String employeeId);
}
