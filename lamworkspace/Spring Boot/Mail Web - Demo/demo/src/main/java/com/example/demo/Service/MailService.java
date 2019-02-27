package com.example.demo.Service;

import com.example.demo.DAO.MailDAO;
import com.example.demo.Model.Mail;
import com.sun.mail.imap.IMAPFolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class MailService {

    @Autowired
    MailDAO mailDAO;

    private final static int MAIL_PER_POST = 15;

    public List<Mail> getAll(){
        return (List<Mail>) mailDAO.findAll();
    }

    public Mail getById(int id){
        return mailDAO.getOne(id);
    }
//
//    public List<Mail> search(String s){
//        return mailDAO.findByNameContaning(s);
//    }

    public void save(Mail mail){
        mailDAO.save(mail);
    }

    public void delete(int id){
        mailDAO.deleteById(id);
    }

    public void create(){
        String host = "pop.gmail.com";
        String mailStoreType = "pop3";
        String user = "thanhlamkma@gmail.com";
        String password = "lam261198";
        List<Mail> listMail = new ArrayList<>();

        try {
            Properties properties = new Properties();
            properties.put("mail.pop3.host", host);
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.starttls.enable", true);

            Session session = Session.getDefaultInstance(properties);

            Store store = session.getStore("pop3s");
            store.connect(host, user, password);

            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);

            Message[] message = folder.getMessages();
            System.out.println("===========" + message.length);


            for (int i = 0, n = message.length; i < n; i++) {
                Mail mail = new Mail();
                Message messages = message[i];
                String title = messages.getSubject().toString();
                String emailSender = messages.getFrom()[0].toString();
//                String text = messages.getContent();
                int message_id = messages.getMessageNumber();

                mail.setTitle(title);
                mail.setEmailSender(emailSender);
//                mail.setContent(text);


                mailDAO.save(mail);
            }



            System.out.println("success");


        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
