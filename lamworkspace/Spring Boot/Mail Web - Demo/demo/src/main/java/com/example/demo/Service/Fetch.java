package com.example.demo.Service;

import com.example.demo.DAO.MailDAO;
import com.example.demo.Model.Mail;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


@Service
public class Fetch {
    @Autowired
    private MailDAO mailDAO;
    public void fetch(){
        String host = "pop.gmail.com";
        String mailStoreType = "pop3";
        String user = "lam848520@gmail.com";
        String password = "Lam261198";
        List<Mail> listMail = new ArrayList<>();

        try {
            Properties properties = new Properties();
            properties.put("mail.pop3.host", host);
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.starttls.enable", true);

            Session session = Session.getDefaultInstance(properties);

            Store store = session.getStore("pop3s");
            store.connect(host, user, password);

            Folder  folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);

            Message[] message = folder.getMessages();
            System.out.println("=====++====" + message.length);


            for (int i = 0, n = message.length; i < n; i++) {

                Mail mail = new Mail();
                Message messages = message[i];
                String newLine = "\n---------------------------------";
                String emailNumber = "\nEmail Number " + (i + 1);
                String subject = messages.getSubject();
                String from = messages.getFrom()[0].toString();

                String content= messages.getContent().toString();
                String body = getContentText(messages);
                String summary = body;
                String html;
                Document document = Jsoup.parse(summary);
                String text = document.body().text();
                int id = messages.getMessageNumber();

                mail.setContent(text);
                mail.setEmailSender(from);
                mail.setTitle(subject);
                mail.setId(id);
                mail.setSummary(summary);

                mailDAO.save(mail);
            }

        } catch (
                NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static String getContentText(Part p) throws MessagingException, IOException {

        if (p.isMimeType("text/*")) {
            String s = getTextContent(p);
            return s;
        }

        if (p.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart)p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    if (text == null)
                        text = getContentText(bp);
                    continue;
                } else if (bp.isMimeType("text/html")) {
                    String s = getContentText(bp);
                    if (s != null)
                        return s;
                } else {
                    return getContentText(bp);
                }
            }
            return text;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart)p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getContentText(mp.getBodyPart(i));
                if (s != null)
                    return s;
            }
        }

        return null;
    }
    private static String getTextContent(Part p) throws IOException, MessagingException {
        try {
            return (String)p.getContent();
        } catch (UnsupportedEncodingException e) {
            OutputStream os = new ByteArrayOutputStream();
            p.writeTo(os);
            String raw = os.toString();
            os.close();

            //cp932 -> Windows-31J
            raw = raw.replaceAll("cp932", "ms932");

            InputStream is = new ByteArrayInputStream(raw.getBytes());
            Part newPart = new MimeBodyPart(is);
            is.close();

            return (String)newPart.getContent();
        }
    }
}
