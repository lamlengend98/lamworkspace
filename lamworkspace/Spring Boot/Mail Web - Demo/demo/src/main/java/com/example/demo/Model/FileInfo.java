package com.example.demo.Model;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Table(name = "attachment")
public class FileInfo {
    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_email")
    private int id_toEmail;

    @Column(name = "fileName")
    private String fileName;

    @Column(name = "url")
    private String url;


    public FileInfo(String fileName, String url) {
        this.fileName = fileName;
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_toEmail() {
        return id_toEmail;
    }

    public void setId_toEmail(int id_toEmail) {
        this.id_toEmail = id_toEmail;
    }

    public FileInfo() {
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "id=" + id +
                ", id_toEmail=" + id_toEmail +
                ", fileName='" + fileName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
