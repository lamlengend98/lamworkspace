//package com.example.demoFlyway;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import javax.mail.*;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Properties;
//
//@Controller
//public class controller {
//    @GetMapping("/")
//    public String emailGet(Model model){
//        String host ="pop.gmail.com";
//        String storeType = "pop3";
//        String user = null;
//        String password = null;
//        try {
//            Properties properties = new Properties();
//            properties.put("mail.pop3.host", host);
//            properties.put("mail.pop3.port", "995");
//            properties.put("mail.pop3.starttls.enable", true);
//
//            Session session = Session.getDefaultInstance(properties);
//
//            Store store = session.getStore("pop3s");
//            store.connect(host, user, password);
//
//            Folder folder = store.getFolder("INBOX");
//            folder.open(Folder.READ_ONLY);
//
//            Message[] messages = folder.getMessages();
//
//            ArrayList<String> list = new ArrayList<>();
//
//            for (int i = 0; i<messages.length; i++){
//                Message message = messages[i];
//                int emailNumber = (i+1);
//                String title = message.getSubject();
//                String emailSender = message.getFrom()[0].toString();
//                String content = message.getContent().toString();
//
//                model.addAttribute("list", message);
//                model.addAttribute("emailNumber", emailNumber);
//                model.addAttribute("title", title);
//                model.addAttribute("emailSender", emailNumber);
//                model.addAttribute("content", content);
//            }
//            model.addAttribute("user", user);
//            model.addAttribute("password", password);
//
//
//        } catch (NoSuchProviderException e) {
//            e.printStackTrace();
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "list";
//    }
//}
