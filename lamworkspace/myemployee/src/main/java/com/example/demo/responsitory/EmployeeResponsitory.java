package com.example.demo.responsitory;

import com.example.demo.bean.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeResponsitory extends JpaRepository<Employee, Integer> {
    List<Employee> findByNameContaining(String q);
}
