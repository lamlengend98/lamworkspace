package com.example.demo.DAO;

import javax.mail.*;
import java.io.IOException;
import java.util.*;
import com.example.demo.Model.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MailDAO extends PagingAndSortingRepository<Mail, Integer> {
//    List<Mail> findByNameContaning(String q);
//
//    List<Mail> findByIdNotNull(Pageable pageable);
//
//    List<Mail> findById(int id);
//
    Mail getOne(int id);
//
//    @Query(value = "insert into mail(title, emailSender, content) values(:title, :emailSender, :content)", nativeQuery = true)
//    void create(@Param("title") String title, @Param("emailSender") String emailSender, @Param("content") String content);


}


