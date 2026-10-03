package repository;

import model.Department;
import java.util.ArrayList;
import java.util.List;

/**
 * InMemoryDepartmentRepository - ArrayList-based department storage.
 */
public class InMemoryDepartmentRepository implements DepartmentRepository {
    private static ArrayList<Department> departments = new ArrayList<>();

    @Override
    public void save(Department department) {
        departments.add(department);
    }

    @Override
    public Department findById(String departmentCode) {
        for (Department department : departments) {
            if (department.getDepartmentCode().equalsIgnoreCase(departmentCode)) {
                return department;
            }
        }
        return null;
    }

    @Override
    public List<Department> findAll() {
        return new ArrayList<>(departments);
    }

    @Override
    public void update(Department department) {
        for (int i = 0; i < departments.size(); i++) {
            if (departments.get(i).getDepartmentCode().equalsIgnoreCase(department.getDepartmentCode())) {
                departments.set(i, department);
                return;
            }
        }
    }

    @Override
    public void delete(String departmentCode) {
        departments.removeIf(department -> department.getDepartmentCode().equalsIgnoreCase(departmentCode));
    }

    @Override
    public boolean exists(String departmentCode) {
        return findById(departmentCode) != null;
    }
}