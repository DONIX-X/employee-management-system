package service;

import model.Employee;
import repository.EmployeeRepository;
import repository.InMemoryEmployeeRepository;
import java.util.ArrayList;
import java.util.List;

/**
 * EmployeeService - Business logic layer for employee operations.
 */
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeService() {
        this.employeeRepository = new InMemoryEmployeeRepository();
    }

    public void addEmployee(Employee employee) throws IllegalArgumentException {
        validateEmployee(employee);
        if (employeeRepository.findByEmployeeCode(employee.getEmployeeCode()) != null) {
            throw new IllegalArgumentException("Employee code already exists");
        }
        employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(int employeeId) throws IllegalArgumentException {
        Employee employee = employeeRepository.findById(employeeId);
        if (employee == null) {
            throw new IllegalArgumentException("Employee with ID " + employeeId + " not found");
        }
        return employee;
    }

    public void updateEmployee(Employee employee) throws IllegalArgumentException {
        validateEmployee(employee);
        if (!employeeRepository.exists(employee.getId())) {
            throw new IllegalArgumentException("Employee with ID " + employee.getId() + " not found");
        }
        Employee existing = employeeRepository.findByEmployeeCode(employee.getEmployeeCode());
        if (existing != null && existing.getId() != employee.getId()) {
            throw new IllegalArgumentException("Employee code already exists");
        }
        employeeRepository.update(employee);
    }

    public void deleteEmployee(int employeeId) throws IllegalArgumentException {
        if (!employeeRepository.exists(employeeId)) {
            throw new IllegalArgumentException("Employee with ID " + employeeId + " not found");
        }
        employeeRepository.delete(employeeId);
    }

    public List<Employee> searchEmployee(int employeeId) {
        List<Employee> result = new ArrayList<>();
        Employee employee = employeeRepository.findById(employeeId);
        if (employee != null) {
            result.add(employee);
        }
        return result;
    }

    public List<Employee> searchEmployee(String query) {
        List<Employee> result = new ArrayList<>();
        if (query == null || query.trim().isEmpty()) {
            return result;
        }
        String searchText = query.trim().toLowerCase();
        for (Employee employee : employeeRepository.findAll()) {
            if (employee.getName().toLowerCase().contains(searchText)
                    || employee.getEmployeeCode().toLowerCase().contains(searchText)) {
                result.add(employee);
            }
        }
        return result;
    }

    public int getTotalEmployees() {
        return employeeRepository.findAll().size();
    }

    private void validateEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        if (employee.getName() == null || employee.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be empty");
        }
        if (employee.getAge() < 18 || employee.getAge() > 100) {
            throw new IllegalArgumentException("Employee age must be between 18 and 100");
        }
        if (employee.getEmployeeCode() == null || employee.getEmployeeCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee code cannot be empty");
        }
        if (employee.getDepartmentCode() == null || employee.getDepartmentCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Department cannot be empty");
        }
        if (employee.getDesignation() == null || employee.getDesignation().trim().isEmpty()) {
            throw new IllegalArgumentException("Designation cannot be empty");
        }
    }
}