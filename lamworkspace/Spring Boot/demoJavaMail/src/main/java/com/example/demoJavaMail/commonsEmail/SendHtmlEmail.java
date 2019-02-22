package com.example.demoJavaMail.commonsEmail;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.net.MalformedURLException;
import java.net.URL;

public class SendHtmlEmail {
    public static void main(String[] args) {
        try{
            String red = "red";
            HtmlEmail htmlEmail = new HtmlEmail();

            htmlEmail.setHostName("smtp.googlemail.com");
            htmlEmail.setSmtpPort(465);
            htmlEmail.setSSLOnConnect(true);
            htmlEmail.setAuthenticator(new DefaultAuthenticator(Constants.MY_EMAIL, Constants.MY_PASSWORD));
            htmlEmail.setFrom(Constants.MY_EMAIL);

            htmlEmail.addTo(Constants.FRIEND_EMAIL);

            htmlEmail.setSubject("Test html email");

            URL url = new URL("https://znews-photo.zadn.vn/w660/Uploaded/ngtmns/2016_09_17/1_1.jpg");
            String cid = htmlEmail.embed(url, "Anh phong canh");

            htmlEmail.setHtmlMsg("<html><h2><font color=\"red\">The apache logo</font><h2>  <img src=\"cid:"
                    + cid + "\"></html>");

            htmlEmail.setTextMsg("testing...");

            htmlEmail.send();

            System.out.println("Sent");
        } catch (EmailException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
