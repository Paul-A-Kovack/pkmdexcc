package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Service for creating and retrieving {@link Compensation} data entities.
 */
@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    private final CompensationRepository compensationRepository;

    @Autowired
    public CompensationServiceImpl(CompensationRepository compensationRepository) {
        this.compensationRepository = compensationRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Compensation create(Compensation compensation) {
        LOG.debug("Creating compensation [{}]", compensation);
        validateCompensation(compensation);
        compensation.setCompensationId(UUID.randomUUID().toString());

        return compensationRepository.save(compensation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Compensation> read(String employeeId) {
        LOG.debug("Finding compensation for employee with id [{}]", employeeId);

        return compensationRepository.findByEmployee_EmployeeId(employeeId);
    }

    // Dev note: If there were more validation rules, might make sense to pull this into its own class (ex. CompensationValidator.validate).

    /**
     * Validate {@link Compensation} for completeness.
     *
     * @param compensation compensation to be validated
     * @throws IllegalArgumentException if the compensation is missing information.
     */
    private void validateCompensation(Compensation compensation) {
        List<String> validationErrors = new ArrayList<>();
        if (compensation == null) {
            validationErrors.add("Compensation must be provided.");
        } else {
            validationErrors.addAll(validateEmployee(compensation.getEmployee()));
            if (compensation.getSalary() == null) {
                validationErrors.add("Salary must be provided.");
            }
            if (compensation.getEffectiveDate() == null) {
                validationErrors.add("Effective date must be provided.");
            }
        }

        if (!validationErrors.isEmpty()) {
            throw new IllegalArgumentException("Unable to create Compensation due to the following error(s):\n"
                    + String.join("\n", validationErrors));
        }
    }

    /**
     * Validate {@link Employee}. Intended to be used as a helper method to {@link #validateCompensation(Compensation)}.
     *
     * @param employee employee from a compensation to be validated
     * @return list of human-readable error messages.
     */
    private List<String> validateEmployee(Employee employee) {
        List<String> validationErrors = new ArrayList<>();
        if (employee == null) {
            validationErrors.add("Employee must be provided.");
        } else if (employee.getEmployeeId() == null || Strings.isBlank(employee.getEmployeeId())) {
            validationErrors.add("Employee id must be provided.");
        }
        return validationErrors;
    }
}
