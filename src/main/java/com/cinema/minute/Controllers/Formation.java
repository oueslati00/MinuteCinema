package com.cinema.minute.Controllers;

import com.cinema.minute.Service.FormationService;
import com.cinema.minute.Service.MyResourceHttpRequestHandler;
import com.cinema.minute.ui.Model.Request.FormationRequests.FormationRequest;
import com.cinema.minute.ui.Model.Response.FormationResponse.formationResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
@Api(description = "formation service ")
@RestController
public class Formation {

    private FormationService formationService;
    @Autowired
    private MyResourceHttpRequestHandler handler;

    @Autowired
    public Formation(FormationService formationService) {
        this.formationService = formationService;
    }

    @ApiOperation(value = " get formation pour l'interface de display de detail de formation ")
    @GetMapping(value = "/api/user/formation/{id}")
    public ResponseEntity<?> getFormationById(@PathVariable("id") Integer id) {
        formationResponse formation = formationService.getFormationDescription(id);
        return new ResponseEntity<>(formation, HttpStatus.OK);

    }

    @ApiOperation(value = "get liste des formation avec qq information pour l'interface de liste de formation ")
    // this get define only name and DateTo start Formateur name
    @GetMapping(value = "/api/user/formation/list")
    public ResponseEntity<List<?>> getListFormation() {
        List<?> list = formationService.getFormationListInformation();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @ApiOperation(value="cette requette est utiliser pour ajouter un nouveau formation mais sans video  ")
    @PostMapping(value = "/api/formateur/formation")
    public ResponseEntity<?> addFormation(@Valid @RequestBody FormationRequest formationRequest) {
        System.out.println(formationRequest);
       formationResponse formationResponse = formationService.AddFormation(formationRequest);
        return new ResponseEntity<>(formationResponse , HttpStatus.OK);
    }
@ApiOperation(value="ajouter des videos de formation associe achaque lesson")
    @PostMapping(value = "/api/formateur/formation/cours/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<?> addVideoForCours(@RequestParam MultipartFile file, @PathVariable Long id) {
        formationService.AddVideo(file, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
 @ApiOperation(value="streaming de chaque video a travers l'id de chaque lesson  ")
    @GetMapping("/api/user/formation/byterange/{idcour}")
    public void byterange(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer idcour ) throws ServletException, IOException {
        // replace mpf variabale de type file with the required file from data base
        File f = formationService.getResource(idcour);
        if(f.exists()){
            System.out.println("ok ");
        }
        request.setAttribute(MyResourceHttpRequestHandler.ATTR_FILE, f);
        handler.handleRequest(request, response);
    }
}
