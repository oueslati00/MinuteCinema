package com.cinema.minute.Controllers;

import com.cinema.minute.Service.CompteRenduService;
import com.cinema.minute.ui.Model.Request.CompteRenduRequest.compteRendurequest;
import com.cinema.minute.ui.Model.Request.VideoDkikaRequest;
import com.cinema.minute.ui.Model.Response.videoDkikaResposne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/compteRendu")
public class CompteRendu {


    //TODO : error occur was checked when we upload file name that already exist
    // you will find it storage service in upload method

    @Autowired
    private CompteRenduService compteRenduService;

    @GetMapping(value = "/all")
    public ResponseEntity<?> getCompteRendu() {
        List<?> compteRenduList = compteRenduService.getAll();
        return new ResponseEntity<>(compteRenduList, HttpStatus.OK);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getCompteRenduById(@PathVariable Integer id) {
        Object test = compteRenduService.get(id);
        return new ResponseEntity<>(test, HttpStatus.OK);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> addCompteRendu(@RequestParam MultipartFile file, @RequestParam Long courId, @RequestParam Long userId) {
        System.out.println(courId + "user " + userId);
        System.out.println("cool");
        compteRenduService.addCompteRendu(file, courId, userId);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateCompteRendu(@RequestBody VideoDkikaRequest vd, @PathVariable Integer id) {
        compteRenduService.updateVideo(vd, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> removeCompteRendu(@PathVariable Integer id) {
        compteRenduService.removeVideo(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
