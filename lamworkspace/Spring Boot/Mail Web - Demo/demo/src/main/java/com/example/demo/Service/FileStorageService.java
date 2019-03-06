package com.example.demo.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import com.example.demo.DAO.FileStorage;
import com.example.demo.DAO.ToMailDAO;
import com.example.demo.DAO.fileDAO;
import com.example.demo.Model.FileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService implements FileStorage {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private final Path rootLocation = Paths.get("filestorage");

    @Autowired
    ToMailDAO toMailDAO;
    FileInfo fileInfo;

    @Autowired
    fileDAO fileDAO;


    @Override
    public void store(MultipartFile file){
        try {
            Path fp = rootLocation.resolve(System.currentTimeMillis()+"");
            if (!Files.exists(fp)){
                Files.createDirectory(fp);
            }
            Path p =fp.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(),p);
            System.out.println(p);
            fileInfo.setUrl(p.toString());
//            fileInfo.setFileName(file.getOriginalFilename());
            fileDAO.save(fileInfo);
        } catch (Exception e) {
            throw new RuntimeException("FAIL! -> message = " + e.getMessage());
        }
    }
    @Override
    public Resource loadFile(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }else{
                throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error! -> message = " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            if(!Files.exists(rootLocation))
            {
                Files.createDirectory(rootLocation);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage!");
        }
    }

    @Override
    public Stream<Path> loadFiles() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new RuntimeException("\"Failed to read stored file");
        }
    }
}
