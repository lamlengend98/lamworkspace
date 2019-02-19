package com.example.demo.service;
import com.example.demo.responsitory.EmployeeResponsitory;
import com.example.demo.bean.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeResponsitory employeeDAO;

    public List<Employee> getAll(){
        return employeeDAO.findAll();
    }

    public Employee getById(int id){
        return employeeDAO.getOne(id);
    }

    public List<Employee> search(String s){
        return employeeDAO.findByNameContaining(s);
    }

    public void save(Employee employee){
        employeeDAO.save(employee);
    }

    public void delete(int id){
        employeeDAO.deleteById(id);
    }
}