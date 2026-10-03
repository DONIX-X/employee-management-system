package model;

/**
 * Employee class - Represents an employee and their organizational details.
 */
public class Employee extends Person {
    private String employeeCode;
    private String departmentCode;
    private String designation;

    public Employee(int id, String name, int age, String phone, String email,
                    String employeeCode, String departmentCode, String designation) {
        super(id, name, age, phone, email);
        this.employeeCode = employeeCode;
        this.departmentCode = departmentCode;
        this.designation = designation;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public void displayDetails() {
        System.out.println("=== EMPLOYEE DETAILS ===");
        System.out.println("Person ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Age: " + getAge());
        System.out.println("Phone: " + getPhone());
        System.out.println("Email: " + getEmail());
        System.out.println("Employee Code: " + employeeCode);
        System.out.println("Department: " + departmentCode);
        System.out.println("Designation: " + designation);
        System.out.println("========================");
    }

    @Override
    public String toString() {
        return employeeCode + " - " + getName() + " (" + designation + ", " + departmentCode + ")";
    }
}