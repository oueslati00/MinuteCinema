package com.cinema.minute.Controllers;

import com.cinema.minute.Service.MyResourceHttpRequestHandler;
import com.cinema.minute.Service.UploadFile.StorageService;
import com.cinema.minute.Service.VideoDkikaService;
import com.cinema.minute.ui.Model.Request.VideoDkikaRequest;
import com.cinema.minute.ui.Model.Response.videoDkikaResposne;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;
@Api(description = "rebrique videodkika service ")
@AllArgsConstructor
@RestController
public class videoDkikaController {
    @Autowired
    VideoDkikaService videoDkikaService;
    @Autowired
    private MyResourceHttpRequestHandler handler;

    @ApiOperation(value="get description of all video dkika")
    @GetMapping(value = "api/user/videoDkika/all")
    public ResponseEntity<?> getAllVideoDkika(){
       List<?> videos =  videoDkikaService.getAll();
        return new ResponseEntity<>(videos, HttpStatus.OK);
    }

    @ApiOperation(value="get description of video dkika a travers son id ")
    @GetMapping(value = "api/user/videoDkika/{id}")
    public ResponseEntity<videoDkikaResposne> getById(@PathVariable Integer id){
        videoDkikaResposne v =videoDkikaService.get(id);
        return new ResponseEntity<>(v ,HttpStatus.OK);
    }

    @ApiOperation(value="upload video dkika")
    @PostMapping(value = "api/formateur/videoDkika/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<?> uploadVideo(@RequestParam MultipartFile file){
        Integer id = videoDkikaService.uploadVideo(file);
        return new ResponseEntity<>(id,HttpStatus.OK);
    }

    @ApiOperation(value="")
    @PostMapping(value = "api/formateur/videoDkika/video")
    public ResponseEntity<?> addVideo(@RequestBody VideoDkikaRequest vd){
        videoDkikaService.addVideo(vd);
        return new ResponseEntity<>(HttpStatus.OK);
    }

   /* @PutMapping(value = "api/admin/videoDkika/{id}")
    public ResponseEntity<?> updateVideo(@RequestBody VideoDkikaRequest vd ,@PathVariable Integer id ){
        videoDkikaService.updateVideo(vd ,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }*/

    @ApiOperation(value="remove video dkika a travers son Id ")
    @DeleteMapping(value = "api/admin/videoDkika/{id}")
    public ResponseEntity<?> deleteVideo(@PathVariable Integer id){
        videoDkikaService.removeVideo(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value="streaming of video dkika by son Id ")
   @GetMapping("api/user/Videodkika/byterange/{id}")
   public void byterange(HttpServletRequest request, HttpServletResponse response,@PathVariable Integer id ) throws ServletException, IOException {
      // replace mpf variabale de type file with the required file from data base
         File f = videoDkikaService.getResource(id);
           if(f.exists()){
           System.out.println("ok ");
       }
       request.setAttribute(MyResourceHttpRequestHandler.ATTR_FILE, f);

       handler.handleRequest(request, response);
   }
}
