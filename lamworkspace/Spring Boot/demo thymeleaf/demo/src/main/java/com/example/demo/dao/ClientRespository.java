package com.example.demo.dao;

import com.example.demo.model.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientRespository extends PagingAndSortingRepository<Employee, Integer> {
}
