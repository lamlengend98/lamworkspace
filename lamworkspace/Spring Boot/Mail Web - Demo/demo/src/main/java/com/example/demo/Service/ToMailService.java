package com.example.demo.Service;

import com.example.demo.DAO.ToMailDAO;
import com.example.demo.Model.Mail;
import com.example.demo.Model.SendMail;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ToMailService {
    @Autowired
    ToMailDAO toMailDAO;

    private final static int MAIL_PER_POST = 15;

    public List<SendMail> getAll(){
        return (List<SendMail>) toMailDAO.findAll();
    }

    public SendMail getById(int id){
        return toMailDAO.getOne(id);
    }

    public void save(SendMail mail){
        toMailDAO.save(mail);
    }

    public void delete(int id){
        toMailDAO.deleteById(id);
    }
}
