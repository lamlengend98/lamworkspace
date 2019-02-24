package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.example.demo.dao.EmployeeDAO;
import com.example.demo.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    EmployeeDAO employeeDAO;

    private final static int EMP_PER_PAGE = 3;

    public List<Employee> getAll(){
        return (List<Employee>) employeeDAO.findAll();
    }

    public Employee getById(int id){
//        List<Employee> list = employeeDAO.findById(id);
//        if(list.size() >= 1){
//            return list.get(0);
//        } else {
//            return null;
//        }
        return  employeeDAO.getOne(id);
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
