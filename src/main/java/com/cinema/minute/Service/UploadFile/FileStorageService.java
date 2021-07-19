package com.cinema.minute.Service.UploadFile;

import com.cinema.minute.Data.Entity.UploadFile;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

public interface FileStorageService {
       void init();

       UploadFile save(MultipartFile file);

       Resource load(String filename);

       void deleteAll();

       List<Path> loadAll();

       void deleteById(Integer id);
}
