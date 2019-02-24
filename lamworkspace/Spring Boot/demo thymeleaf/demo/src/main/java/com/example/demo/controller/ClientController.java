package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

//import com.example.demo.dao.EmployeeDAO;
import com.example.demo.dao.EmployeeDAO;
import com.example.demo.model.Employee;
import com.example.demo.model.PageModel;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.PageWrapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public ModelAndView homepage(@RequestParam("pageSize") int pageSize,
                                 @RequestParam("page") int page) {
        page = page - 1;
        List<Employee> relativeSentAtEmailList = new ArrayList<Employee>();
        PageRequest pageRequest = new PageRequest(page, PAGE_SIZE, Sort.Direction.DESC, "sentAt");
        Page<Employee> pages = search == null || search.length() == 0? mailBoxService.list(pageRequest) : mailBoxService.searchContent(search, pageRequest);
        List<Employee> list = pages.getContent();
        int rowsInPage = list.size();
        PageWrapper<Employee> pageWrapper = new PageWrapper<Employee>(pages, "/admin/mailbox");
        for(int i = 0; i < rowsInPage; i++){
            Email email = list.get(i);
            RelativeSentAtEmail relativeSentAtEmail = new RelativeSentAtEmail(email);
            List<EmailAccount> listAccount = mailAccountsService.findById(email.getAccountId());
            EmailAccount emailAccount = listAccount.size() > 0 ? listAccount.get(0) : null;
            String emailAccountAddress = emailAccount != null ? emailAccount.getAccount() : "Unknown";
            relativeSentAtEmail.setAccount(emailAccountAddress);
            relativeSentAtEmailList.add(relativeSentAtEmail);
        }
        if(search != null && search.length() > 0){
            model.addAttribute("search", search);
        }
        int fromEntry = rowsInPage == 0 ? 0 : page * PAGE_SIZE + 1;
        int toEntry = rowsInPage == 0 ? 0 : fromEntry + rowsInPage - 1;
        model.addAttribute("list", relativeSentAtEmailList);
        model.addAttribute("page", pageWrapper);
        model.addAttribute("fromEntry", fromEntry);
        model.addAttribute("toEntry", toEntry);
        return modelAndView;
    }

}