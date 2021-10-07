package com.mindex.challenge.controller;

import com.mindex.challenge.service.ReportingStructureService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

/**
 * Unit test for {@link ReportingStructureController}.
 */
@ExtendWith(MockitoExtension.class)
class ReportingStructureControllerUnitTest {

    @Mock
    private ReportingStructureService mockReportingStructureService;

    @InjectMocks
    private ReportingStructureController controller;

    @Test
    void read_CallsExpected() {
        String employeeId = UUID.randomUUID().toString();
        controller.read(employeeId);
        verify(mockReportingStructureService, only()).read(employeeId);
    }
}