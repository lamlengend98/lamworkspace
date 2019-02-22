package com.example.demoJavaMail.commonsEmail;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class SingleTextEmail {
    public static void main(String[] args) {
        try {
            Email email = new SimpleEmail();

            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator(Constants.MY_EMAIL,
                    Constants.MY_PASSWORD));

            email.setSSLOnConnect(true);
            email.setFrom(Constants.MY_EMAIL);
            email.setSubject("Test Email");
            email.setMsg("Hey abcz....");

            email.addTo(Constants.FRIEND_EMAIL);
            email.send();
            System.out.println("Sent");
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}
