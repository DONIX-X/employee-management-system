package repository;

import model.Department;
import java.util.List;

/**
 * DepartmentRepository interface - Defines department data access operations.
 */
public interface DepartmentRepository {
    void save(Department department);
    Department findById(String departmentCode);
    List<Department> findAll();
    void update(Department department);
    void delete(String departmentCode);
    boolean exists(String departmentCode);
}