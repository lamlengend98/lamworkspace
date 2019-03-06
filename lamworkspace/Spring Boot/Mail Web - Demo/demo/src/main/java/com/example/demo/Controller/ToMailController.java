package com.example.demo.Controller;

import com.example.demo.DAO.FileStorage;
import com.example.demo.DAO.MailDAO;
import com.example.demo.DAO.ToMailDAO;
import com.example.demo.DAO.fileDAO;
import com.example.demo.Model.*;
import com.example.demo.Response.AjaxResponseBody;
import com.example.demo.Service.Fetch;
import com.example.demo.Service.MailService;
import com.example.demo.Service.ToMailService;
import org.apache.commons.mail.*;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.jws.WebParam;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ToMailController {
    Logger logger = LoggerFactory.getLogger(ToMailController.class);
    @Autowired
    private ToMailDAO toMailDAO;

    @Autowired
    FileStorage fileStorage;

    @Autowired
    fileDAO fileDAO;

    @Autowired
    private static final int BUTTONS_TO_SHOW = 3;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 5;
    private static final int[] PAGE_SIZES = {5, 10};

    @GetMapping("/toEmail/send")
    public String send(Model model, MultipartFile file){
        model.addAttribute("mail", new SendMail());
        return "send";
    }

    @GetMapping("/file")
    public String file(){
        return "index";
    }


    @GetMapping("/toEmail")
    public ModelAndView toEmail(@RequestParam(value = "pageSize") Optional<Integer> pageSize,
                                @RequestParam(value = "page") Optional<Integer> page){
        if (toMailDAO.count() != 0) {

        } else {
            toMailDAO.findAll();
        }
        ModelAndView modelAndView = new ModelAndView("toEmail");
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<SendMail> clientlist = toMailDAO.findAll(new PageRequest(evalPage, evalPageSize));

//        System.out.println("client list get total pages " + clientlist.getTotalPages() + " client list get number " + clientlist.getNumber());
        Pagination pager = new Pagination(clientlist.getTotalPages(), clientlist.getNumber(), BUTTONS_TO_SHOW);
//        System.out.println("list: "+page);
        modelAndView.addObject("list", clientlist);

        modelAndView.addObject("selectedPageSize", evalPageSize);

        modelAndView.addObject("pageSizes", PAGE_SIZES);

        modelAndView.addObject("pager", pager);
        return modelAndView;
    }

    @PostMapping("/toEmail/upload")
    @ResponseBody
    public ResponseEntity<?> singleFileUpload(@RequestParam("file") MultipartFile file,
                                              RedirectAttributes redirectAttributes) {
        FileInfo fileInfo = new FileInfo();
        AjaxResponseBody ajaxResponseBody = new AjaxResponseBody();

        try {
            Path fp = rootLocation.resolve(System.currentTimeMillis()+"");
            if (!Files.exists(fp)){
                Files.createDirectory(fp);
            }
            Path p =fp.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(),p);
//            System.out.println(p);
            fileInfo.setUrl(p.toString());
            fileInfo.setFileName(file.getOriginalFilename());
//            fileInfo.setFileName(file.getOriginalFilename());
            fileDAO.save(fileInfo);
            List<Integer> list = new ArrayList<Integer>();
            list.add(fileInfo.getId());
            for (int listID: list
                 ) {
                System.out.println(listID);
            }

            //
            ajaxResponseBody.setList(list);
            ajaxResponseBody.setMsg("done");
            ajaxResponseBody.setStatus(true);
            return ResponseEntity.ok(ajaxResponseBody);
        } catch (Exception e) {
            logger.error("singleFileUpload: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private final Path rootLocation = Paths.get("filestorage");
    String urlfile;
    @PostMapping("/toEmail/save")
    public String save(@RequestBody FormJSON formJSON) throws IOException {
        FileInfo fileInfo = new FileInfo();
        System.out.println("============="+formJSON.toString());
        String myEmail = "lam848520@gmail.com";
        String myPassword = "Lam261198";
        String title = formJSON.getSubject();
        String email1 = formJSON.getReceiver();
        String content = formJSON.getContent();
        List<Integer> attachment1 = formJSON.getAttachment();
//        for (Integer list: attachment1
//        ) {
//            SendMail sendMail = new SendMail();
//            System.out.println(sendMail.getId());
//            Optional<FileInfo> byId = fileDAO.findById(list);
//            System.out.println(byId.get().getId());
//            byId.get().setId_toEmail(1);
//        }


        SendMail sendMail = new SendMail();
        System.out.println(sendMail.getId());
//        System.out.println(file);
//        try {
//            Path fp = rootLocation.resolve(System.currentTimeMillis()+"");
//            if (!Files.exists(fp)){
//                Files.createDirectory(fp);
//            }
//            Path p =fp.resolve(file.getOriginalFilename());
//            Files.copy(file.getInputStream(),p);
//            urlfile = p.toString();
//            System.out.println(urlfile);
//            System.out.println("++++++++++++++++++++");
//            System.out.println(file);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        if (urlfile==null){
//            try {
//                HtmlEmail email = new HtmlEmail();
//                email.setHostName("smtp.googlemail.com");
//                email.setSmtpPort(465);
//                email.setAuthenticator(new DefaultAuthenticator(myEmail, myPassword));
//
//                email.setSSLOnConnect(true);
//                email.setFrom(email1);
//                email.setSubject(title);
//                email.setHtmlMsg(content);
//                Date date = new Date();
//
//                sendMail.setTitle(title);
//                sendMail.setContent(content);
//                sendMail.setToEmail(email1);
//                sendMail.setCreate_at(date);
//
//                email.setFrom(myEmail);
//                email.setSubject(title);
//                email.setHtmlMsg(content);
//
//                toMailDAO.save(sendMail);
//                email.addTo(email1);
//                email.send();
//                System.out.println("Sent");
//            } catch (EmailException e) {
//                e.printStackTrace();
//            }
//
//        }
//        else {
            FileInfo f = new FileInfo();
            List<Integer> attachment2 = formJSON.getAttachment();
            System.out.println(attachment1.size());
            try {
//                System.out.println(file.getOriginalFilename());
                List<String> url2 = new ArrayList<>();

                Date date = new Date();

                Document document = Jsoup.parse(content);
                String text = document.body().text();
                sendMail.setTitle(title);
                sendMail.setContent(text);
                sendMail.setToEmail(email1);
                sendMail.setCreate_at(date);
//                sendMail.setFileName(file.getOriginalFilename());
                

                System.out.println("===================="+sendMail.getUrl());
                EmailAttachment attachment = new EmailAttachment();

                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                for (int i = 0; i < attachment1.size(); i++){
                    Optional<FileInfo> byId = fileDAO.findById(attachment1.get(i));
                    url2.add("/home/lam/workspace/lamworkspace/Spring Boot/Mail Web - Demo/demo/"+byId.get().getUrl());
                    for (String list2: url2
                         ) {
                        System.out.println(list2);
                        attachment.setPath(list2);
                        mimeBodyPart.attachFile(list2);
                        attachment.setDisposition(EmailAttachment.ATTACHMENT);
                    }
                }


//                attachment.setName(f.getFileName());
                
                //tao doi tuong
                MultiPartEmail email = new MultiPartEmail();
                email.setHostName("smtp.googlemail.com");
                email.setSmtpPort(465);
                email.setSSLOnConnect(true);
                email.setAuthenticator(new DefaultAuthenticator(myEmail, myPassword));
                email.setFrom(myEmail);
                email.setSubject(title);

                email.setMsg(text);
                email.attach(attachment);

                toMailDAO.save(sendMail);
                email.addTo(email1);
                email.send();
                System.out.println("Sent");
            } catch (EmailException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
//        }
        return "redirect:/toEmail";
    }
}
