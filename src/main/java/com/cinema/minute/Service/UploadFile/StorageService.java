package com.cinema.minute.Service.UploadFile;

import com.cinema.minute.Data.Entity.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class StorageService implements  FileStorageService {
    @Value("${upload.path}")
    private String path;

    @Autowired
    private FileInDB fileInDB;

    @Override
    public void init() {
        try {
            Files.createDirectory(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public UploadFile save(MultipartFile file) {
        int i =1;
        // check if the file Exist in this Folder
        Path p = Paths.get(path).resolve(file.getName());

        while (Files.exists(p))
        {
            System.out.println(file.getName());
            System.out.println(file.getContentType());
            p=Paths.get(path).resolve( i++ +file.getOriginalFilename() );
        }

        UploadFile uploadFile = null;
        try {
            Files.copy(file.getInputStream(), p);
                uploadFile = fileInDB.addFile(file,p);

        } catch (Exception e) {

        }
        return  uploadFile;
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = Paths.get(path).resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            System.out.println(resource.exists() + " resource exist");
            System.out.println(resource.isReadable() + " resource readable ");
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(Paths.get(path).toFile());
        fileInDB.deleteAll();
    }

    public List<Path> loadAll() {
        try {
            Path root = Paths.get(path);
            if (Files.exists(root)) {
                return Files.walk(root, 1)
                        .filter(path -> !path.equals(root))
                        .collect(Collectors.toList());
            }

            return Collections.emptyList();
        } catch (IOException e) {
            throw new RuntimeException("Could not list the files!");
        }
    }
    @Override
    public void deleteById(Integer id) {
        UploadFile f =fileInDB.getFileById(id);
        try{
            Files.delete(Paths.get(f.getUrlFile()));
        }catch (IOException e){
            System.out.println(" cannot remove this file from folder" +e.getMessage());
        }
        finally {
            fileInDB.removeFileById(id);
        }
    }

    // change file name method
    private Path RenameMethod(Path p,MultipartFile file){
        System.out.println( "file name is " + p.getFileName());
        System.out.println("path value is " + p);

        return null;
    }
}
