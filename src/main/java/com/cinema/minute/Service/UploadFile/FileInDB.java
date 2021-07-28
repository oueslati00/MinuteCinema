package com.cinema.minute.Service.UploadFile;


import com.cinema.minute.Data.Entity.UploadFile;
import com.cinema.minute.Data.Repository.FileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Service
public class FileInDB {
    @Value("${upload.path}")
    private String path;

    @Autowired
    private FileRepo fileRepo;

    public List<UploadFile> getlistofFile(){
       return fileRepo.findAll();
    }

    public UploadFile addFile(MultipartFile file, Path p){
        UploadFile file1 = new UploadFile();
        file1.setName(file.getName());
        file1.setTypeFile(file.getContentType());
        file1.setUrlFile(p.toString());
        fileRepo.save(file1);
        return file1;
    }
    public void removeFileById(Integer id){
        fileRepo.deleteById(id);
    }

    public void deleteAll(){
        fileRepo.deleteAll();
    }

    public UploadFile getFileById(Integer id){
        return fileRepo.findById(id).orElseThrow(()-> new RuntimeException(" file not found exception"));
    }

}
