package repository;

import model.Employee;
import java.util.ArrayList;
import java.util.List;

/**
 * InMemoryEmployeeRepository - ArrayList-based employee storage.
 */
public class InMemoryEmployeeRepository implements EmployeeRepository {
    private static ArrayList<Employee> employees = new ArrayList<>();
    private static int nextId = 1;

    @Override
    public void save(Employee employee) {
        if (employee.getId() == 0) {
            employee.setId(nextId++);
        } else if (employee.getId() >= nextId) {
            nextId = employee.getId() + 1;
        }
        employees.add(employee);
    }

    @Override
    public Employee findById(int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }

    @Override
    public Employee findByEmployeeCode(String employeeCode) {
        for (Employee employee : employees) {
            if (employee.getEmployeeCode().equalsIgnoreCase(employeeCode)) {
                return employee;
            }
        }
        return null;
    }

    @Override
    public List<Employee> findAll() {
        return new ArrayList<>(employees);
    }

    @Override
    public void update(Employee employee) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId() == employee.getId()) {
                employees.set(i, employee);
                return;
            }
        }
    }

    @Override
    public void delete(int id) {
        employees.removeIf(employee -> employee.getId() == id);
    }

    @Override
    public boolean exists(int id) {
        return findById(id) != null;
    }

    public void clear() {
        employees.clear();
        nextId = 1;
    }
}