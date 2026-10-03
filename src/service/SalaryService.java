package service;

import model.Salary;
import repository.InMemorySalaryRepository;
import repository.SalaryRepository;
import java.time.YearMonth;
import java.util.List;

/**
 * SalaryService - Business logic layer for employee payroll operations.
 */
public class SalaryService {
    private SalaryRepository salaryRepository;

    public SalaryService(SalaryRepository salaryRepository) {
        this.salaryRepository = salaryRepository;
    }

    public SalaryService() {
        this.salaryRepository = new InMemorySalaryRepository();
    }

    public void addSalary(Salary salary) {
        validateSalary(salary);
        if (salaryRepository.exists(salary.getEmployeeCode(), salary.getPayPeriod())) {
            throw new IllegalArgumentException("Salary record already exists for this employee and pay period");
        }
        salaryRepository.save(salary);
    }

    public Salary getSalary(String employeeCode, String payPeriod) {
        Salary salary = salaryRepository.findById(employeeCode, payPeriod);
        if (salary == null) {
            throw new IllegalArgumentException("Salary record not found");
        }
        return salary;
    }

    public List<Salary> getAllSalaries() {
        return salaryRepository.findAll();
    }

    public List<Salary> getSalariesForEmployee(String employeeCode) {
        return salaryRepository.findByEmployeeCode(employeeCode);
    }

    public void updateSalary(Salary salary) {
        validateSalary(salary);
        if (!salaryRepository.exists(salary.getEmployeeCode(), salary.getPayPeriod())) {
            throw new IllegalArgumentException("Salary record not found");
        }
        salaryRepository.update(salary);
    }

    public void deleteSalary(String employeeCode, String payPeriod) {
        if (!salaryRepository.exists(employeeCode, payPeriod)) {
            throw new IllegalArgumentException("Salary record not found");
        }
        salaryRepository.delete(employeeCode, payPeriod);
    }

    public double getTotalPayroll(String payPeriod) {
        double total = 0;
        for (Salary salary : salaryRepository.findAll()) {
            if (salary.getPayPeriod().equals(payPeriod)) {
                total += salary.getNetSalary();
            }
        }
        return total;
    }

    private void validateSalary(Salary salary) {
        if (salary == null) {
            throw new IllegalArgumentException("Salary cannot be null");
        }
        if (salary.getEmployeeCode() == null || salary.getEmployeeCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee code cannot be empty");
        }
        try {
            YearMonth.parse(salary.getPayPeriod());
        } catch (RuntimeException exception) {
            throw new IllegalArgumentException("Pay period must use YYYY-MM format");
        }
        if (!isValidAmount(salary.getBaseSalary()) || !isValidAmount(salary.getBonus())
                || !isValidAmount(salary.getDeductions())) {
            throw new IllegalArgumentException("Salary amounts must be non-negative numbers");
        }
        if (salary.getDeductions() > salary.getBaseSalary() + salary.getBonus()) {
            throw new IllegalArgumentException("Deductions cannot exceed base salary plus bonus");
        }
    }

    private boolean isValidAmount(double amount) {
        return !Double.isNaN(amount) && !Double.isInfinite(amount) && amount >= 0;
    }
}