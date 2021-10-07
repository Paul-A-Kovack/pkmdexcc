package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

/**
 * Unit test for {@link CompensationController}.
 */
@ExtendWith(MockitoExtension.class)
public class CompensationControllerUnitTest {

    @Mock
    private CompensationService mockCompensationService;

    @InjectMocks
    private CompensationController controller;

    @Test
    public void create_CallsExpected() {
        Compensation compensation = new Compensation();
        controller.create(compensation);
        verify(mockCompensationService, only()).create(compensation);
    }

    @Test
    public void read_CallsExpected() {
        String employeeId = UUID.randomUUID().toString();
        controller.read(employeeId);
        verify(mockCompensationService, only()).read(employeeId);
    }
}
