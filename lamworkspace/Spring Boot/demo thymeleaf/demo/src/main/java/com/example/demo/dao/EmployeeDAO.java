package com.example.demo.dao;

import com.example.demo.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface EmployeeDAO extends PagingAndSortingRepository<Employee, Integer> {
    List<Employee> findByNameContaining(String q);

    Page<Employee> findByIdNotNull(Pageable pageable);

    List<Employee> findById(int id);

    Employee getOne(int id);
}
