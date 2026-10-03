package model;

/**
 * Salary class - Stores a monthly payroll record for an employee.
 */
public class Salary {
    private String employeeCode;
    private String payPeriod;
    private double baseSalary;
    private double bonus;
    private double deductions;

    public Salary(String employeeCode, String payPeriod, double baseSalary, double bonus, double deductions) {
        this.employeeCode = employeeCode;
        this.payPeriod = payPeriod;
        this.baseSalary = baseSalary;
        this.bonus = bonus;
        this.deductions = deductions;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getPayPeriod() {
        return payPeriod;
    }

    public void setPayPeriod(String payPeriod) {
        this.payPeriod = payPeriod;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public double getDeductions() {
        return deductions;
    }

    public void setDeductions(double deductions) {
        this.deductions = deductions;
    }

    public double getNetSalary() {
        return baseSalary + bonus - deductions;
    }
}