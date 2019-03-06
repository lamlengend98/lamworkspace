package com.example.demo.Model;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FormJSON {
    private String subject;
    private String receiver;
    private String content;
    private List<Integer> attachment;

    public FormJSON() {
    }

    public FormJSON(String subject, String receiver, String content, List<Integer> attachment) {
        this.subject = subject;
        this.receiver = receiver;
        this.content = content;
        this.attachment = attachment;
    }

    public List<Integer> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<Integer> attachment) {
        this.attachment = attachment;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "FormJSON{" +
                "subject='" + subject + '\'' +
                ", receiver='" + receiver + '\'' +
                ", content='" + content + '\'' +
                ", attachment=" + attachment +
                '}';
    }
}
