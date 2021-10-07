# Coding Challenge

## What's Provided

A simple [Spring Boot](https://projects.spring.io/spring-boot/) web application has been created and bootstrapped with
data. The application contains information about all employees at a company. On application start-up, an in-memory Mongo
database is bootstrapped with a serialized snapshot of the database. While the application runs, the data may be
accessed and mutated in the database without impacting the snapshot.

### How to Run

The application may be executed by running `gradlew bootRun`.

### How to Use

The following endpoints are available to use:

```
* CREATE
    * HTTP Method: POST 
    * URL: localhost:8080/employee
    * PAYLOAD: Employee
    * RESPONSE: Employee
* READ
    * HTTP Method: GET 
    * URL: localhost:8080/employee/{id}
    * RESPONSE: Employee
* UPDATE
    * HTTP Method: PUT 
    * URL: localhost:8080/employee/{id}
    * PAYLOAD: Employee
    * RESPONSE: Employee
```

The Employee has a JSON schema of:

```json
{
  "type": "Employee",
  "properties": {
    "employeeId": {
      "type": "string"
    },
    "firstName": {
      "type": "string"
    },
    "lastName": {
      "type": "string"
    },
    "position": {
      "type": "string"
    },
    "department": {
      "type": "string"
    },
    "directReports": {
      "type": "array",
      "items": "string"
    }
  }
}
```

For all endpoints that require an "id" in the URL, this is the "employeeId" field.

## What to Implement

Clone or download the repository, do not fork it.

### Task 1

Create a new type, ReportingStructure, that has two properties: employee and numberOfReports.

For the field "numberOfReports", this should equal the total number of reports under a given employee. The number of
reports is determined to be the number of directReports for an employee and all of their distinct reports. For example,
given the following employee structure:

```
                    John Lennon
                /               \
         Paul McCartney         Ringo Starr
                               /        \
                          Pete Best     George Harrison
```

The numberOfReports for employee John Lennon (employeeId: 16a596ae-edd3-4847-99fe-c4518e82c86f) would be equal to 4.

This new type should have a new REST endpoint created for it. This new endpoint should accept an employeeId and return
the fully filled out ReportingStructure for the specified employeeId. The values should be computed on the fly and will
not be persisted.

### Task 2

Create a new type, Compensation. A Compensation has the following fields: employee, salary, and effectiveDate. Create
two new Compensation REST endpoints. One to create and one to read by employeeId. These should persist and query the
Compensation from the persistence layer.

## Delivery

Please upload your results to a publicly accessible Git repo. Free ones are provided by Github and Bitbucket.

## What Was Implemented

### Task 1

New endpoint was made available to be able to retrieve ReportingStructure data:

```
* READ
    * HTTP Method: GET 
    * URL: localhost:8080/reporting-structures/{employeeId}
    * RESPONSE: ReportingStructure
```

The ReportingStructure has a JSON schema of:

```json
{
  "type": "ReportingStructure",
  "employee": {
    "type": "Employee"
  },
  "numberOfReports": {
    "type": "number"
  }
}
```

### Task 2
Two new endpoints were made available to be able to create and retrieve Compensation data:
```
* CREATE
    * HTTP Method: POST 
    * URL: localhost:8080/compensations
    * PAYLOAD: Compensation
    * RESPONSE: Compensation
* READ
    * HTTP Method: GET 
    * URL: localhost:8080/compensations/{employeeId}
    * RESPONSE: Compensation[]

```
The Compensation has a JSON schema of:

```json
{
  "type": "Compensation",
  "compensationId": {
    "type": "string"
  },
  "employee": {
    "type": "Employee"
  },
  "salary": {
    "type": "number"
  },
  "effectiveDate": {
    "type": "string"
  }
}
```

Here are some example usages, using cURL:
```
curl -i -H "Content-Type:application/json" -d '{"employee":{"employeeId": "16a596ae-edd3-4847-99fe-c4518e82c86f"}, "salary": 100001, "effectiveDate": "2021-10-07"}' -X POST "localhost:8080/compensations"

curl -i -H "Accept:application/json" "localhost:8080/compensations/16a596ae-edd3-4847-99fe-c4518e82c86f"
```