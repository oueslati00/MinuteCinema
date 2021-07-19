package com.cinema.minute.Controllers;

import com.cinema.minute.Service.UploadFile.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class FileUploadHelper {


    private static  StorageService storatgeService;


    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public static  ResponseEntity<?> upload(@RequestParam MultipartFile file){
        storatgeService.save(file);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public static ResponseEntity<List<File>> getListFiles() {
        List<File> fileInfos = storatgeService.loadAll()
                .stream()
                .map(x->x.toFile())
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK)
                .body(fileInfos);
    }


    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public static ResponseEntity<Resource> getFile(@PathVariable String filename) {
        System.out.println(filename);
        Resource file = storatgeService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @DeleteMapping("/{id}")
    public static ResponseEntity<?> deleteFile(@PathVariable Integer id ){
        storatgeService.deleteById(id);
        return ResponseEntity.ok().body("the file was delete");
    }
}
