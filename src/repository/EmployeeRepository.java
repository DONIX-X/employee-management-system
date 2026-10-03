package repository;

import model.Employee;
import java.util.List;

/**
 * EmployeeRepository interface - Defines employee data access operations.
 */
public interface EmployeeRepository {
    void save(Employee employee);
    Employee findById(int id);
    Employee findByEmployeeCode(String employeeCode);
    List<Employee> findAll();
    void update(Employee employee);
    void delete(int id);
    boolean exists(int id);
}