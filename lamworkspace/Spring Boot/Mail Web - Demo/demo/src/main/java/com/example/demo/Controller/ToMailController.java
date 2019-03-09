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

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.jws.WebParam;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
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
    public String send(Model model, MultipartFile file) {
        model.addAttribute("mail", new SendMail());
        return "send";
    }

    @GetMapping("/file")
    public String file() {
        return "index";
    }


    @GetMapping("/toEmail")
    public ModelAndView toEmail(@RequestParam(value = "pageSize") Optional<Integer> pageSize,
                                @RequestParam(value = "page") Optional<Integer> page) {
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
            Path fp = rootLocation.resolve(System.currentTimeMillis() + "");
            if (!Files.exists(fp)) {
                Files.createDirectory(fp);
            }
            Path p = fp.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), p);
//            System.out.println(p);
            fileInfo.setUrl(p.toString());
            fileInfo.setFileName(file.getOriginalFilename());
//            fileInfo.setFileName(file.getOriginalFilename());
            fileDAO.save(fileInfo);
            List<Integer> list = new ArrayList<Integer>();
            list.add(fileInfo.getId());
            for (int listID : list
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
    public String save(@RequestBody FormJSON formJSON) throws IOException, MessagingException {
        FileInfo fileInfo = new FileInfo();
        System.out.println("=============" + formJSON.toString());
        String myEmail = "lam848520@gmail.com";
        String myPassword = "Lam261198";
        String title = formJSON.getSubject();
//        String email1 = formJSON.getReceiver();
        String content = formJSON.getContent();
        System.out.println(content);
        List<Integer> attachment1 = formJSON.getAttachment();
        List<String> receiver = formJSON.getReceiver();

        Multipart multipart = new MimeMultipart();
        MimeBodyPart messageBodyPart = new MimeBodyPart();

        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(myEmail, myPassword);
                    }
                });

        try {
            SendMail sendMail = new SendMail();
            Message message = new MimeMessage(session);
            for (String receive : receiver
            ) {
                System.out.println(receive);
                message.setFrom(new InternetAddress(myEmail));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(receive));
                message.setSubject(title);
                message.setText(content);
                Iterable<SendMail> all = toMailDAO.findAll();
                List<Integer> listID = new ArrayList<>();
                for (SendMail iterable : all
                ) {
                    listID.add(iterable.getId());
                }
//            System.out.println(Collections.max(listID));
//            fileInfo.setId_toEmail(Collections.max(listID));
                messageBodyPart = new MimeBodyPart();
                List<Integer> attachmentID = formJSON.getAttachment();
                for (int i = 0; i < attachmentID.size(); i++) {
                    Optional<FileInfo> byId = fileDAO.findById(attachmentID.get(i));
                    String file = "/home/lam/workspace/lamworkspace/Spring Boot/Mail Web - Demo/demo/" + byId.get().getUrl();
                    String fileName = byId.get().getFileName();

                    addAttachment(multipart, file, fileName);

                    byId.get().setId_toEmail(Collections.max(listID));
                    message.setContent(multipart);
                }

                System.out.println("Sending");

                Transport.send(message);
                Date date = new Date();
                saveMail(toMailDAO, sendMail, date, receive, title,content);
                System.out.println("Done");


            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }

//        }
        return "redirect:/toEmail";
    }

    private static void saveMail(ToMailDAO toMailDAO, SendMail sendMail, Date date, String receiver, String title, String content){
        sendMail.setCreate_at(date);
        sendMail.setContent(content);
        sendMail.setTitle(title);
        sendMail.setToEmail(receiver);
        toMailDAO.save(sendMail);
    }

    private static void addAttachment(Multipart multipart, String file, String filename) throws MessagingException {
        DataSource source = new FileDataSource(file);
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename);
        multipart.addBodyPart(messageBodyPart);
    }

}
