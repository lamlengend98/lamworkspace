package com.example.demo.Controller;

import com.example.demo.DAO.MailDAO;
import com.example.demo.DAO.ToMailDAO;
import com.example.demo.Model.Mail;
import com.example.demo.Model.Pagination;
import com.example.demo.Model.SendMail;
import com.example.demo.Service.Fetch;
import com.example.demo.Service.MailService;
import com.example.demo.Service.ToMailService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class ToMailController {
    @Autowired
    private ToMailDAO toMailDAO;


    @Autowired
    private static final int BUTTONS_TO_SHOW = 3;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 5;
    private static final int[] PAGE_SIZES = {5, 10};

    @GetMapping("/toEmail/send")
    public String send(Model model){
        model.addAttribute("mail", new SendMail());
        return "send";
    }

    @GetMapping("/toEmail")
    public ModelAndView homepage(@RequestParam(value = "pageSize") Optional<Integer> pageSize,
                                 @RequestParam(value = "page") Optional<Integer> page) {

        if (toMailDAO.count() != 0) {
            ;//pass
        } else {
            toMailDAO.findAll();
        }
        ModelAndView modelAndView = new ModelAndView("send");
        //
        // Evaluate page size. If requested parameter is null, return initial
        // page size
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        // print repo
        Page<SendMail> clientlist = toMailDAO.findAll(new PageRequest(evalPage, evalPageSize));
        System.out.println("list: "+clientlist);
        System.out.println("client list get total pages " + clientlist.getTotalPages() + " client list get number " + clientlist.getNumber());
        Pagination pager = new Pagination(clientlist.getTotalPages(), clientlist.getNumber(), BUTTONS_TO_SHOW);
        // add clientmodel
        modelAndView.addObject("mail", clientlist);
        // evaluate page size
        modelAndView.addObject("selectedPageSize", evalPageSize);
        // add page sizes
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        // add pager
        modelAndView.addObject("pager", pager);
        return modelAndView;
    }


    @PostMapping("/toEmail/save")
    public String save(Model model, String toEmail, HttpServletRequest request){
        String myEmail = "lam848520@gmail.com";
        String myPassword = "Lam261198";
        String title = request.getParameter("title");
        String email1 = request.getParameter("toEmail");
        String content = request.getParameter("content");
        System.out.println("============="+title);
        SendMail sendMail = new SendMail();
        try {
            Email email = new SimpleEmail();

            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator(myEmail, myPassword));

            email.setSSLOnConnect(true);
            email.setFrom(email1);
            email.setSubject(title);
            email.setMsg(content);

            Date date = new Date();

            sendMail.setTitle(title);
            sendMail.setContent(content);
            sendMail.setToEmail(email1);
            sendMail.setCreate_at(date);

            toMailDAO.save(sendMail);
            email.addTo(toEmail);
            email.send();
            System.out.println("Sent");
        } catch (EmailException e) {
            e.printStackTrace();
        }
        model.addAttribute("mail", new SendMail());
        return "redirect:/toEmail";
    }
}
