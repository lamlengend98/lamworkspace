package com.example.demoJavaMail.commonsEmail;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;

import java.net.MalformedURLException;
import java.net.URL;

public class SendHtmlEmbedImageEmail {
    public static void main(String[] args) {
        try{
          String htmlEmail = "<h2>Hello</h2>" +
                  "<h3>This is a test email</h3>" +
                  "<p>Anyone here??</p>" +
                  "<img src='-sbLpHXW-Q98/UOb-aouCdTI/AAAAAAAAOHw/b8Wp4w8Q10s/s1600/Silent-Morning.png' />";

            ImageHtmlEmail imageHtmlEmail = new ImageHtmlEmail();

            imageHtmlEmail.setHostName("smtp.googlemail.com");
            imageHtmlEmail.setSmtpPort(465);
            imageHtmlEmail.setSSLOnConnect(true);
            imageHtmlEmail.setAuthenticator(new DefaultAuthenticator(Constants.MY_EMAIL, Constants.MY_PASSWORD));

            imageHtmlEmail.setFrom(Constants.MY_EMAIL);
            imageHtmlEmail.addTo(Constants.FRIEND_EMAIL);

            URL url = new URL("https://1.bp.blogspot.com/");

            imageHtmlEmail.setDataSourceResolver(new DataSourceUrlResolver(url));

            imageHtmlEmail.setHtmlMsg(htmlEmail);

            imageHtmlEmail.setTextMsg("Hello, your HTML message not support");

            imageHtmlEmail.send();

            System.out.println("Sent!!");

        } catch (EmailException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
