package com.cinema.minute.Controllers;

import com.cinema.minute.Service.CompteRenduService;
import com.cinema.minute.Service.MyResourceHttpRequestHandler;
import com.cinema.minute.ui.Model.Request.CompteRenduRequest.compteRendurequest;
import com.cinema.minute.ui.Model.Request.VideoDkikaRequest;
import com.cinema.minute.ui.Model.Response.videoDkikaResposne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("")
public class CompteRendu {


    @Autowired
    private CompteRenduService compteRenduService;
    @Autowired
    private MyResourceHttpRequestHandler handler;

    @GetMapping(value = "/api/admin/compteRendu/all")
    public ResponseEntity<?> getCompteRendu() {
        List<?> compteRenduList = compteRenduService.getAll();
        return new ResponseEntity<>(compteRenduList, HttpStatus.OK);
    }
    @GetMapping(value = "/api/admin/compteRendu/file/{id}")
    public ResponseEntity<?> getCompteRenduById(@PathVariable Integer id) {
        Object test = compteRenduService.get(id);
        return new ResponseEntity<>(test, HttpStatus.OK);
    }

    @PostMapping(value = "/api/user/compteRendu", consumes = {"multipart/form-data"})
    public ResponseEntity<?> addCompteRendu(@RequestParam MultipartFile file, @RequestParam Long courId, @RequestParam Long userId) {
        System.out.println(courId + "user " + userId);
        System.out.println("cool");
        compteRenduService.addCompteRendu(file, courId, userId);
        return new ResponseEntity<>(HttpStatus.OK);

    }
    @GetMapping("/api/admin/compteRendu/byterange/{id}")

    public void byterange (HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id ) throws ServletException, IOException {
        File f = compteRenduService.load(id);
        if(f.exists()){
            System.out.println("ok ");
        }
        request.setAttribute(MyResourceHttpRequestHandler.ATTR_FILE, f);
        handler.handleRequest(request, response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateCompteRendu(@RequestBody VideoDkikaRequest vd, @PathVariable Integer id) {
        compteRenduService.updateCompteRendu(vd, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/admin/compteRendu/{id}")
    public ResponseEntity<?> removeCompteRendu(@PathVariable Integer id) {
        compteRenduService.removeCompteRendu(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
