package com.cinema.minute.Controllers;

import com.cinema.minute.Service.UploadFile.StorageService;
import com.cinema.minute.Service.VideoDkikaService;
import com.cinema.minute.ui.Model.Request.VideoDkikaRequest;
import com.cinema.minute.ui.Model.Response.videoDkikaResposne;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/videoDkika")
public class videoDkikaController {
    @Autowired
    VideoDkikaService videoDkikaService;


    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllVideoDkika(){
       List<?> videos =  videoDkikaService.getAll();
        return new ResponseEntity<>(videos, HttpStatus.OK);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<videoDkikaResposne> getById(@PathVariable Integer id){
        videoDkikaResposne v =videoDkikaService.get(id);
        return new ResponseEntity<>(v ,HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> addVideo(@RequestBody VideoDkikaRequest vd){
        videoDkikaService.addVideo(vd);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateVideo(@RequestBody VideoDkikaRequest vd ,@PathVariable Integer id ){
        videoDkikaService.updateVideo(vd ,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteVideo(@PathVariable Integer id){
        videoDkikaService.removeVideo(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
