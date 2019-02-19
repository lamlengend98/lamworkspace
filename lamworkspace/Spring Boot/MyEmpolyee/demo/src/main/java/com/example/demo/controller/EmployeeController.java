package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

//    @GetMapping("/employee")
//    public String index(@RequestParam(value = "id", defaultValue = "1") int page, Model model) {
//        model.addAttribute("employees", employeeService.getAll());
//        return "list";
//    }

    @GetMapping("/employee/create")
    public String create(Model model) {
        model.addAttribute("employee", new Employee());
        return "form";
    }

    @GetMapping("/employee/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("employee", employeeService.getById(id));
        return "form";
    }

    @PostMapping("/employee/save")
    public String save(@Valid Employee employee, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return "form";
        }
        employeeService.save(employee);
        redirect.addFlashAttribute("success", "Saved employee successfully!");
        return "redirect:/employee";
    }

    @GetMapping("/employee/{id}/delete")
    public String delete(@PathVariable int id, RedirectAttributes redirect) {
        employeeService.delete(id);
        redirect.addFlashAttribute("success", "Deleted employee successfully!");
        return "redirect:/employee";
    }

    @GetMapping("/employee/search")
    public String search(@RequestParam("s") String s, Model model) {
        if (s.equals("")) {
            return "redirect:/employee";
        }

        model.addAttribute("employees", employeeService.search(s));
        return "list";
    }

    @GetMapping("/employee/page/{page}")
    public String fetchPage(HttpServletRequest httpServletRequest,
                            @PathVariable int page,
                            Model model){
        PagedListHolder<?> pages = (PagedListHolder<?>) httpServletRequest.getSession().getAttribute("employees");
        int pageSize = 3;
        List<Employee> list = employeeService.getAll();
        System.out.println("==========="+list.size());

        if(pages == null){
            pages = new PagedListHolder<>(list);
            pages.setPageSize(pageSize);
            System.out.println("========="+pages.getPageSize());
        } else {
            final int goToPage = page - 1;
            if(goToPage <= pages.getPageCount() && goToPage >= 0){
                pages.setPage(goToPage);
            }
        }
        httpServletRequest.getSession().setAttribute("employees", pages);
        int current = pages.getPage() + 1;
        int begin = Math.max(1, current - list.size());
        int end = Math.min(begin + 5, pages.getPageCount());
        int totalPage = pages.getPageCount();

        String baseUrl = "/employee/page/";
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("baseUrl", baseUrl);
        model.addAttribute("employees", pages);

        return "list";
    }
}
