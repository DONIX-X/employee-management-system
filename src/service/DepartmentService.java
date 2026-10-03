package service;

import model.Department;
import repository.DepartmentRepository;
import repository.InMemoryDepartmentRepository;
import java.util.ArrayList;
import java.util.List;

/**
 * DepartmentService - Business logic layer for department operations.
 */
public class DepartmentService {
    private DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public DepartmentService() {
        this.departmentRepository = new InMemoryDepartmentRepository();
    }

    public void addDepartment(Department department) {
        validateDepartment(department);
        if (departmentRepository.exists(department.getDepartmentCode())) {
            throw new IllegalArgumentException("Department code already exists");
        }
        departmentRepository.save(department);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(String departmentCode) {
        Department department = departmentRepository.findById(departmentCode);
        if (department == null) {
            throw new IllegalArgumentException("Department not found: " + departmentCode);
        }
        return department;
    }

    public void updateDepartment(Department department) {
        validateDepartment(department);
        if (!departmentRepository.exists(department.getDepartmentCode())) {
            throw new IllegalArgumentException("Department not found: " + department.getDepartmentCode());
        }
        departmentRepository.update(department);
    }

    public void deleteDepartment(String departmentCode) {
        if (!departmentRepository.exists(departmentCode)) {
            throw new IllegalArgumentException("Department not found: " + departmentCode);
        }
        departmentRepository.delete(departmentCode);
    }

    public List<Department> searchDepartment(String query) {
        List<Department> result = new ArrayList<>();
        if (query == null || query.trim().isEmpty()) {
            return result;
        }
        String searchText = query.trim().toLowerCase();
        for (Department department : departmentRepository.findAll()) {
            if (department.getDepartmentCode().toLowerCase().contains(searchText)
                    || department.getName().toLowerCase().contains(searchText)) {
                result.add(department);
            }
        }
        return result;
    }

    public int getTotalDepartments() {
        return departmentRepository.findAll().size();
    }

    private void validateDepartment(Department department) {
        if (department == null) {
            throw new IllegalArgumentException("Department cannot be null");
        }
        if (department.getDepartmentCode() == null || department.getDepartmentCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Department code cannot be empty");
        }
        if (department.getName() == null || department.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Department name cannot be empty");
        }
    }
}