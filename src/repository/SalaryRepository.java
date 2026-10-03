package repository;

import model.Salary;
import java.util.List;

/**
 * SalaryRepository interface - Defines employee payroll data access operations.
 */
public interface SalaryRepository {
    void save(Salary salary);
    Salary findById(String employeeCode, String payPeriod);
    List<Salary> findAll();
    List<Salary> findByEmployeeCode(String employeeCode);
    void update(Salary salary);
    void delete(String employeeCode, String payPeriod);
    boolean exists(String employeeCode, String payPeriod);
}