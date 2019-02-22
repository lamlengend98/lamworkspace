package com.example.demoJavaMail.commonsEmail;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

import java.net.MalformedURLException;
import java.net.URL;

public class EmailWithAttachment2 {

    public static void main(String[] args) {
        try {
            //Tạo đối tượng đính kèm
            EmailAttachment emailAttachment = new EmailAttachment();
            emailAttachment.setURL(new URL("https://znews-photo.zadn.vn/w660/Uploaded/ngtmns/2016_09_17/1_1.jpg"));
            emailAttachment.setDisposition(EmailAttachment.ATTACHMENT);
            emailAttachment.setName("View");

            //Tạo đối tượng Email
            MultiPartEmail multiPartEmail = new MultiPartEmail();

            //Cáu hình
            multiPartEmail.setHostName("smtp.googlemail.com");
            multiPartEmail.setSmtpPort(465);
            multiPartEmail.setSSLOnConnect(true);
            multiPartEmail.setAuthenticator(new DefaultAuthenticator(Constants.MY_EMAIL, Constants.MY_PASSWORD));
            multiPartEmail.setFrom(Constants.MY_EMAIL, "chelsea");
            multiPartEmail.addTo(Constants.FRIEND_EMAIL, "kamen");

            multiPartEmail.setSubject("View now");
            multiPartEmail.setMsg("Ảnh phong cảnh");

            multiPartEmail.attach(emailAttachment);

            multiPartEmail.send();

            System.out.println("sent");
        } catch (EmailException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
