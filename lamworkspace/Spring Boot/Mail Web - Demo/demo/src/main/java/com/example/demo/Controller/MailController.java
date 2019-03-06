package com.example.demo.Controller;

import com.example.demo.DAO.MailDAO;
import com.example.demo.Model.Mail;
import com.example.demo.Model.Pagination;
import com.example.demo.Model.SendMail;
import com.example.demo.Service.Fetch;
import com.example.demo.Service.MailService;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.*;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Controller
public class MailController {
    @Autowired
    private MailDAO mailDAO;

    @Autowired
    MailService mailService;

    @Autowired
    Fetch fetch;
    private static final int BUTTONS_TO_SHOW = 3;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 5;
    private static final int[] PAGE_SIZES = {5, 10};

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id, Model model){
        mailService.delete(id);
        System.out.println("adasdasd");
        return "redirect:/";
    }



    @GetMapping("/{id}")
    public String get(@PathVariable("id") int id, Model model){
        model.addAttribute("mail", mailService.getById(id));
        return "get";
    }

    @GetMapping("/")
    public ModelAndView homepage(@RequestParam(value = "pageSize") Optional<Integer> pageSize,
                                 @RequestParam(value = "page") Optional<Integer> page) {
        fetch.fetch();

        if (mailDAO.count() != 0) {
            ;//pass
        } else {
            mailDAO.findAll();
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
        Page<Mail> clientlist = (Page<Mail>) mailDAO.findAll(new PageRequest(evalPage, evalPageSize));
        System.out.println("list: "+clientlist);
        System.out.println("client list get total pages " + clientlist.getTotalPages() + " client list get number " + clientlist.getNumber());
        Pagination pager = new Pagination(clientlist.getTotalPages(), clientlist.getNumber(), BUTTONS_TO_SHOW);
        // add clientmodel
        modelAndView.addObject("list", clientlist);
        // evaluate page size
        modelAndView.addObject("selectedPageSize", evalPageSize);
        // add page sizes
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        // add pager
        modelAndView.addObject("pager", pager);
        return modelAndView;
    }





}
