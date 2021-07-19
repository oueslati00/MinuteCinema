package com.cinema.minute.Controllers;

import com.cinema.minute.Service.CompteRenduService;
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

import java.util.List;

@RestController
@RequestMapping("/api/compteRendu")
public class CompteRendu {


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
    @GetMapping("/test/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        System.out.println(filename);
        Resource file = compteRenduService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateCompteRendu(@RequestBody VideoDkikaRequest vd, @PathVariable Integer id) {
        compteRenduService.updateCompteRendu(vd, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> removeCompteRendu(@PathVariable Integer id) {
        compteRenduService.removeCompteRendu(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
