package com.example.demo.controller;

import java.util.Optional;

//import com.example.demo.dao.EmployeeDAO;
import com.example.demo.dao.EmployeeDAO;
import com.example.demo.model.Employee;
import com.example.demo.model.PagerModel;
import com.example.demo.service.EmployeeService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
@Controller
public class ClientController {
    private static final int BUTTONS_TO_SHOW = 3;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 5;
    private static final int[] PAGE_SIZES = {5, 10};

@Autowired
EmployeeDAO employeeDAO;
@GetMapping("/")
public ModelAndView homepage(@RequestParam("pageSize") Optional<Integer> pageSize,
                             @RequestParam("page") Optional<Integer> page) {
    if (employeeDAO.count() != 0) {
        ;//pass
    } else {
        employeeDAO.findAll();
    }
    ModelAndView modelAndView = new ModelAndView("list");
    //
    // Evaluate page size. If requested parameter is null, return initial
    // page size
    int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
    // Evaluate page. If requested parameter is null or less than 0 (to
    // prevent exception), return initial size. Otherwise, return value of
    // param. decreased by 1.
    int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
    // print repo
    System.out.println("here is client repo " + employeeDAO.findAll());
    Page<Employee> clientlist = (Page<Employee>) employeeDAO.findAll(new PageRequest(evalPage, evalPageSize));
    System.out.println("client list get total pages" + clientlist.getTotalPages() + "client list get number " + clientlist.getNumber());
    PagerModel pager = new PagerModel(clientlist.getTotalPages(), clientlist.getNumber(), BUTTONS_TO_SHOW);
    // add clientmodel
    modelAndView.addObject("clientlist", clientlist);
    // evaluate page size
    modelAndView.addObject("selectedPageSize", evalPageSize);
    // add page sizes
    modelAndView.addObject("pageSizes", PAGE_SIZES);
    // add pager
    modelAndView.addObject("pager", pager);
    return modelAndView;
}
}
