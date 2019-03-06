package com.example.demo.DAO;

import com.example.demo.Model.Mail;
import com.example.demo.Model.SendMail;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ToMailDAO extends PagingAndSortingRepository<SendMail, Integer> {
    SendMail getOne(int id);

}
