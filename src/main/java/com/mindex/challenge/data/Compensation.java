package com.mindex.challenge.data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Compensation {
    private String compensationId;
    private Employee employee;
    private BigDecimal salary;
    private LocalDate effectiveDate;

    public String getCompensationId() {
        return compensationId;
    }

    public void setCompensationId(String compensationId) {
        this.compensationId = compensationId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Compensation that = (Compensation) o;
        return Objects.equals(compensationId, that.compensationId)
                && Objects.equals(employee, that.employee)
                && Objects.equals(salary, that.salary)
                && Objects.equals(effectiveDate, that.effectiveDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(compensationId, employee, salary, effectiveDate);
    }

    @Override
    public String toString() {
        return "Compensation{" +
                "compensationId='" + compensationId + '\'' +
                ", employee=" + employee +
                ", salary=" + salary +
                ", effectiveDate=" + effectiveDate +
                '}';
    }
}
