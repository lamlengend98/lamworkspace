package com.example.demo.Model;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "mail")
public class Mail {

    @Id
    @Column(name = "id" , nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", nullable = false)
    @Size(max = 100)
    private String title;

    @Column(name = "email_sender")
    private String emailSender;

    @Column(name = "content")
    private String content;

    @Column(name = "summary")
    private String summary;

    public Mail() {
    }

    public Mail(@Size(max = 100) String title, String emailSender, String content, String summary) {
        this.title = title;
        this.summary = summary;
        this.emailSender = emailSender;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    @Override
    public String toString() {
        return "Mail{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", emailSender='" + emailSender + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public static void main(String[] args) {
        String workingDir = System.getProperty("Baitaplonpttkht.xlsx");
        System.out.println("Current working directory : " + workingDir);
    }
}
