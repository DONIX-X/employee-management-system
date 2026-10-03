package model;

/**
 * Department class - Represents an organizational department.
 */
public class Department {
    private String departmentCode;
    private String name;
    private String location;
    private String description;

    public Department(String departmentCode, String name, String location, String description) {
        this.departmentCode = departmentCode;
        this.name = name;
        this.location = location;
        this.description = description;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }
}