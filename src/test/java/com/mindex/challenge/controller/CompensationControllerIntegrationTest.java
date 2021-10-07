package com.mindex.challenge.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CompensationControllerIntegrationTest {

    private static final String EMPLOYEE_DATABASE = "src/main/resources/static/employee_database.json";

    private String compensationUrl;
    private String compensationWithIdUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        compensationUrl = "http://localhost:" + port + "/compensations";
        compensationWithIdUrl = compensationUrl + "/{employeeId}";
    }

    @Test
    void test_createAndReadCompensation() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Employee storedEmployee = objectMapper.readValue(
                new File(EMPLOYEE_DATABASE),
                new TypeReference<List<Employee>>() {}
        ).get(0);

        Employee employee = new Employee();
        employee.setEmployeeId(storedEmployee.getEmployeeId());

        LocalDate date = LocalDate.now();
        BigDecimal salary = new BigDecimal("100000.00");

        Compensation compensation = new Compensation();
        compensation.setEmployee(employee);
        compensation.setEffectiveDate(date);
        compensation.setSalary(salary);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Compensation createdCompensation =
                restTemplate.exchange(compensationUrl,
                        HttpMethod.POST,
                        new HttpEntity<>(compensation, headers),
                        Compensation.class).getBody();

        Compensation[] retrievedCompensations =
                restTemplate.exchange(compensationWithIdUrl,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        Compensation[].class,
                        employee.getEmployeeId()).getBody();

        assertNotNull(createdCompensation);
        assertEquals(employee, createdCompensation.getEmployee());
        assertEquals(salary, createdCompensation.getSalary());
        assertEquals(date, createdCompensation.getEffectiveDate());

        assertNotNull(retrievedCompensations);
        assertEquals(createdCompensation, retrievedCompensations[0]);
    }
}