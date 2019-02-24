package com.example.javaMailTemplate.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "mail")
public class MailModel implements Serializable {
    /*
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "title", nullable = false)
    @Size(max = 100)
    private String title;

    @Column(name = "emailSender", nullable = false)
    @Size(max = 100)
    private String emailSender;

    @Column(name = "content")
    private String content;

    public MailModel() {
        super();
    }

    public MailModel(@Size(max = 100) String title, @Size(max = 100) String emailSender, String content) {
        this.title = title;
        this.emailSender = emailSender;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmailSender() {
        return emailSender;
    }

    public void setEmailSender(String emailSender) {
        this.emailSender = emailSender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}



