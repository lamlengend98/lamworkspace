package com.example.javaMailTemplate.model;

import javax.persistence.*;

@Entity
@Table(name = "mail")
public class MailModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "subject")
    private String subject;

    @Column(name = "from")
    private String from;

    @Column(name = "text")
    private String text;

    public MailModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MailModel(int id, String subject, String from, String text) {
        this.id = id;
        this.subject = subject;
        this.from = from;
        this.text = text;
    }
}
