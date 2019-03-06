package com.example.demo.DAO;

import com.example.demo.Model.FileInfo;
import com.example.demo.Model.Mail;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileStorage {
    public void store(MultipartFile file);
    public Resource loadFile(String filename);
    public void deleteAll();
    public void init();
    public Stream<Path> loadFiles();
}

