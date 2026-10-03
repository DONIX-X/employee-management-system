package repository;

import model.Salary;
import java.util.ArrayList;
import java.util.List;

/**
 * InMemorySalaryRepository - ArrayList-based payroll storage.
 */
public class InMemorySalaryRepository implements SalaryRepository {
    private static ArrayList<Salary> salaries = new ArrayList<>();

    @Override
    public void save(Salary salary) {
        salaries.add(salary);
    }

    @Override
    public Salary findById(String employeeCode, String payPeriod) {
        for (Salary salary : salaries) {
            if (salary.getEmployeeCode().equalsIgnoreCase(employeeCode)
                    && salary.getPayPeriod().equals(payPeriod)) {
                return salary;
            }
        }
        return null;
    }

    @Override
    public List<Salary> findAll() {
        return new ArrayList<>(salaries);
    }

    @Override
    public List<Salary> findByEmployeeCode(String employeeCode) {
        List<Salary> result = new ArrayList<>();
        for (Salary salary : salaries) {
            if (salary.getEmployeeCode().equalsIgnoreCase(employeeCode)) {
                result.add(salary);
            }
        }
        return result;
    }

    @Override
    public void update(Salary salary) {
        for (int i = 0; i < salaries.size(); i++) {
            Salary existing = salaries.get(i);
            if (existing.getEmployeeCode().equalsIgnoreCase(salary.getEmployeeCode())
                    && existing.getPayPeriod().equals(salary.getPayPeriod())) {
                salaries.set(i, salary);
                return;
            }
        }
    }

    @Override
    public void delete(String employeeCode, String payPeriod) {
        salaries.removeIf(salary -> salary.getEmployeeCode().equalsIgnoreCase(employeeCode)
                && salary.getPayPeriod().equals(payPeriod));
    }

    @Override
    public boolean exists(String employeeCode, String payPeriod) {
        return findById(employeeCode, payPeriod) != null;
    }
}