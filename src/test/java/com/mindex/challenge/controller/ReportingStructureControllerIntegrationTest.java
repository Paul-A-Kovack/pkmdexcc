package com.mindex.challenge.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureControllerIntegrationTest {

    private static final String EMPLOYEE_DATABASE = "src/main/resources/static/employee_database.json";

    private String reportingStructureWithIdUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        reportingStructureWithIdUrl = "http://localhost:" + port + "/reporting-structure/{id}";
    }

    @Test
    public void test_RetrieveReportingStructure_ReturnsExpected() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Employee employee = objectMapper.readValue(
                new File(EMPLOYEE_DATABASE),
                new TypeReference<List<Employee>>() {}
        ).get(0);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ReportingStructure actual =
                restTemplate.exchange(reportingStructureWithIdUrl,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        ReportingStructure.class,
                        employee.getEmployeeId()).getBody();

        ReportingStructure expected = new ReportingStructure();
        expected.setEmployee(employee);
        expected.setNumberOfReports(4);

        assertEquals(actual, expected);
    }
}
