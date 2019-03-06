package com.example.demo.DAO;


import com.example.demo.Model.FileInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface fileDAO extends PagingAndSortingRepository<FileInfo, Integer> {

}
