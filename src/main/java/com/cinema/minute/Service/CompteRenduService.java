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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
            CompteRenduList compteRenduList = new CompteRenduList(x.getUser().getUsername(),x.getCour().getName(),x.getFile().getName(),x.getLocalDateTime());

            return compteRenduList;
        }).collect(Collectors.toList());
       // using manual mapper

    }

    public Object get(Integer id) {
                 CompteRendu test =  compteRenduRepo.findById(id).orElseThrow(()-> new RuntimeException("this file does not exist"));

                 CompteRenduList compteRenduList = new CompteRenduList();

                 compteRenduList.setFilename(test.getFile().getName());
                 compteRenduList.setCourname(test.getCour().getName());
                 compteRenduList.setUsername(test.getUser().getUsername());
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

    public void updateVideo(VideoDkikaRequest vd, Integer id) {
    }

    public void removeVideo(Integer id) {
    }
}
