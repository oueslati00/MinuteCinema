package com.cinema.minute.Service;

import com.cinema.minute.Data.Entity.CompteRendu;
import com.cinema.minute.Data.Entity.Cours;
import com.cinema.minute.Data.Entity.UploadFile;
import com.cinema.minute.Data.Entity.User;
import com.cinema.minute.Data.Repository.CompteRenduRepo;
import com.cinema.minute.Data.Repository.CourRepo;
import com.cinema.minute.Data.Repository.UserRepository;
import com.cinema.minute.Service.UploadFile.StorageService;
import com.cinema.minute.ui.Model.Request.CompteRenduRequest.compteRendurequest;
import com.cinema.minute.ui.Model.Request.VideoDkikaRequest;
import com.cinema.minute.ui.Model.Response.CompteRendu.CompteRenduList;
import com.cinema.minute.ui.Model.Response.videoDkikaResposne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompteRenduService {

    @Autowired
    private StorageService storageService;

    @Autowired
    private CompteRenduRepo compteRenduRepo;

    @Autowired
    private CourRepo courRepo;

    @Autowired
    private UserRepository userRepository;

    public List<?> getAll() {
        return compteRenduRepo.findAll().stream().map(x->{
        CompteRenduList compteRenduList = new CompteRenduList();
            if(x.getUser()!=null)
            compteRenduList.setUsername(x.getUser().getUsername());
            if(x.getFile()!=null)
                compteRenduList.setFilename(x.getFile().getUrlFile());
            if(x.getCour()!=null)
                compteRenduList.setCourname(x.getCour().getName());
            if(x.getLocalDateTime()!=null)
                compteRenduList.setLocalDateTime(x.getLocalDateTime());
           return compteRenduList;


        }).collect(Collectors.toList());
       // using manual mapper

    }

    public Object get(Integer id) {
                 CompteRendu test =  compteRenduRepo.findById(id).orElseThrow(()-> new RuntimeException("this file does not exist"));

                 CompteRenduList compteRenduList = new CompteRenduList();
        if(test.getUser()!=null)
            compteRenduList.setUsername(test.getUser().getUsername());
        if(test.getFile()!=null)
            compteRenduList.setFilename(test.getFile().getUrlFile());
        if(test.getCour()!=null)
            compteRenduList.setCourname(test.getCour().getName());
        if(test.getLocalDateTime()!=null)
            compteRenduList.setLocalDateTime(test.getLocalDateTime());
        return compteRenduList;


    }

    public void addCompteRendu(MultipartFile file, Long CourId,Long userId) {
        // find user and cour
        Cours  cour= courRepo.findById(CourId).orElseThrow(()-> new RuntimeException("this cour does not exist"));
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("this user does not exist"));

        CompteRendu compteRendu = new CompteRendu();
        compteRendu.setCour(cour);
        compteRendu.setUser(user);
        UploadFile uploadFile=storageService.save(file);
        compteRendu.setFile(uploadFile);
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
      compteRendu.setLocalDateTime(localDateTime);
        compteRenduRepo.save(compteRendu);

    }

    public void updateCompteRendu(VideoDkikaRequest vd, Integer id) {
    }

    public void removeCompteRendu(Integer id) {
    }

    public File load(Integer id) throws FileNotFoundException {
       CompteRendu compteRendu= compteRenduRepo.findById(id).orElseThrow(()-> new RuntimeException("this file id does not exist"));
       if(compteRendu.getFile()!= null && compteRendu.getFile().getUrlFile()!=null)
           return new File(compteRendu.getFile().getUrlFile());
       throw new FileNotFoundException("this file does not exist");
    }
}
