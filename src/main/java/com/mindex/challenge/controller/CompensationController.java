package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Provides an API for creating and retrieving {@link Compensation} for employees.
 */
@RestController
public class CompensationController {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    private final CompensationService compensationService;

    @Autowired
    public CompensationController(CompensationService compensationService) {
        this.compensationService = compensationService;
    }

    /**
     * Create new {@link Compensation} for an employee.
     *
     * @param compensation compensation to be created.
     * @return the created compensation entity.
     */
    @PostMapping("/compensations")
    public Compensation create(@RequestBody Compensation compensation) {
        LOG.debug("Received Compensation create request for [{}]", compensation);

        Compensation createdCompensation = compensationService.create(compensation);
        return createdCompensation;
    }

    /**
     * Retrieve all {@link Compensation} entities for an employee.
     *
     * @param employeeId the employee to retrieve compensation for.
     * @return list of all compensation entities for an employee.
     */
    @GetMapping("/compensations/{employeeId}")
    public List<Compensation> read(@PathVariable String employeeId) {
        LOG.debug("Received employee read request for employee id [{}]", employeeId);

        return compensationService.read(employeeId);
    }
}
