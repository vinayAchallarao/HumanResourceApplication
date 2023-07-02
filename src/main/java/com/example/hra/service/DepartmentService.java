package com.example.hra.service;

import com.example.hra.Entity.Department;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface DepartmentService {
    String addDepartment(Department department);
    String modifyDepartment(Department department);
    Department getDepartmentById(BigDecimal departmentId);
    Map<String, BigDecimal> findMaxSalaryByDepartmentId(BigDecimal departmentId);
    Map<String, BigDecimal> findMinSalaryByDepartmentId(BigDecimal departmentId);
    List<Department> getDepartmentsByEmployeeId(String empId);
    void deleteDepartment(BigDecimal departmentId);
}
