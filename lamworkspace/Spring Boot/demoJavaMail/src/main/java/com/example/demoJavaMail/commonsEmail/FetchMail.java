package com.example.demoJavaMail.commonsEmail;

import javax.mail.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class FetchMail {
    public static void fetch(String host, String storeType, String user, String password) {
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

            ArrayList<String> listMail = new ArrayList<>();

            for (int i = 0, n = message.length; i < n; i++) {
                Message messages = message[i];
                String newLine = "\n---------------------------------";
                String emailNumber = "\nEmail Number " + (i + 1);
                String subject = "\nSubject: " + messages.getSubject();
                String from = "\nFrom: " + messages.getFrom()[0];
                String text = "\nText: " + messages.getContent().toString();

                byte newLinee[] = newLine.getBytes();
                byte emailNumberr[] = emailNumber.getBytes();
                byte subjectt[] = subject.getBytes();
                byte fromm[] = from.getBytes();
                byte textt[] = text.getBytes();
                FileOutputStream fileOutputStream = new FileOutputStream("listMail.txt", true);
                fileOutputStream.write(newLinee);
                fileOutputStream.write(emailNumberr);
                fileOutputStream.write(subjectt);
                fileOutputStream.write(fromm);
                fileOutputStream.write(textt);
                fileOutputStream.close();

            }
            System.out.println("success");


            folder.close();
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String host = "pop.gmail.com";
        String mailStoreType = "pop3";
        String user = "kamenriderstrongertora@gmail.com";
        String password = "lam261198";

        fetch(host, mailStoreType, user, password);
    }
}
