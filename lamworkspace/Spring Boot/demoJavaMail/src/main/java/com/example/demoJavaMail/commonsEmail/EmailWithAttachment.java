package com.example.demoJavaMail.commonsEmail;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

public class EmailWithAttachment {

    public static void main(String[] args) {
        try {
//            Tạo đối tượng đính kèm
            EmailAttachment emailAttachment = new EmailAttachment();
            emailAttachment.setPath("/home/lam/Pictures/a.png");
            emailAttachment.setDisposition(EmailAttachment.ATTACHMENT);
            emailAttachment.setDescription("Titans");
            emailAttachment.setName("Teen Titans");

//              Tạo đối tượng
            MultiPartEmail multiPartEmail = new MultiPartEmail();

//            Cấu hình
            multiPartEmail.setHostName("smtp.googlemail.com");
            multiPartEmail.setSmtpPort(465);
            multiPartEmail.setSSLOnConnect(true);
            multiPartEmail.setAuthenticator(new DefaultAuthenticator(Constants.MY_EMAIL, Constants.MY_PASSWORD));

            multiPartEmail.setFrom(Constants.MY_EMAIL, "chelsea");
            multiPartEmail.addTo(Constants.FRIEND_EMAIL, "kamen");

            multiPartEmail.setSubject("Robin and StarFire");
            multiPartEmail.setMsg("Gu mặn vl :))))");

//            Thêm đính kèm
            multiPartEmail.attach(emailAttachment);

//            Gửi
            multiPartEmail.send();

            System.out.println("sent");
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}
