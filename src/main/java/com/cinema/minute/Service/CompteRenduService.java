package com.cinema.minute.Service;

import com.cinema.minute.Data.Entity.*;
import com.cinema.minute.Data.Repository.CompteRenduRepo;
import com.cinema.minute.Data.Repository.CourRepo;
import com.cinema.minute.Data.Repository.FormationRepo;
import com.cinema.minute.Data.Repository.UserRepository;
import com.cinema.minute.Service.UploadFile.StorageService;
import com.cinema.minute.ui.Model.Request.CompteRenduRequest.compteRendurequest;
import com.cinema.minute.ui.Model.Request.VideoDkikaRequest;
import com.cinema.minute.ui.Model.Response.CompteRendu.CompteRenduList;
import com.cinema.minute.ui.Model.Response.UserResponse.UserResponse;
import com.cinema.minute.ui.Model.Response.videoDkikaResposne;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private FormationRepo formationRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<?> getAll() {
        return compteRenduRepo.findAll().stream().map(x->{
        CompteRenduList compteRenduList = new CompteRenduList();
            if(x.getUser()!=null)
            compteRenduList.setUsername(x.getUser().getUsername());
            if(x.getFile()!=null)
                compteRenduList.setFileId(x.getId());
            if(x.getCour()!=null)
                compteRenduList.setCourname(x.getCour().getName());
            if(x.getLocalDateTime()!=null)
                compteRenduList.setLocalDateTime(x.getLocalDateTime());
           return compteRenduList;


        }).collect(Collectors.toList());
       // using manual mapper

    }
    public List<?> getByFormationId(Integer id) {
       List<CompteRendu> compteRendus = formationRepo.findAll().stream().filter(x->x.getId().equals(id)).flatMap(x->x.getChapter().stream()).flatMap(x->x.getCour().stream()).flatMap(x->x.getCompteRendu().stream()).collect(Collectors.toList());

     List<CompteRenduList> compteRenduLists =   compteRendus.stream().map(x->{
            CompteRenduList compteRenduList = new CompteRenduList();
            if(x.getUser()!=null)
                compteRenduList.setUsername(x.getUser().getUsername());
            if(x.getFile()!=null)
                compteRenduList.setFileId(x.getId());
            if(x.getCour()!=null)
                compteRenduList.setCourname(x.getCour().getName());
            if(x.getLocalDateTime()!=null)
                compteRenduList.setLocalDateTime(x.getLocalDateTime());
            return compteRenduList;


        }).collect(Collectors.toList());
        // using manual mapper
      return compteRenduLists;
    }

    public Object get(Integer id) {
                 CompteRendu test =  compteRenduRepo.findById(id).orElseThrow(()-> new RuntimeException("this file does not exist"));

                 CompteRenduList compteRenduList = new CompteRenduList();
        if(test.getUser()!=null)
            compteRenduList.setUsername(test.getUser().getUsername());
        if(test.getFile()!=null)
            compteRenduList.setFileId(test.getId());
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

    public List<CompteRenduList> getListCompteRenduForForamteurById(Long id) {
        // get list of user that add compte Rendu for foramtion that have foramteur Id = id ;
        // step 1 : get list of formation for this foramteur
      List<Formation> listforamtion =  formationRepo.findAll().stream().filter(x-> {
          if(x.getUser() != null)
           return x.getUser().getId().equals(id);
          else
              return false;
      }).collect(Collectors.toList());
      // step 2 get list of cours For this formation
      List<Cours> cours = listforamtion.stream().flatMap(x->x.getChapter().stream()).flatMap(x->x.getCour().stream()).collect(Collectors.toList());
      // step 3 get list list of compte Rendu of this chapter ;
       List<CompteRendu> compteRendus = cours.stream().flatMap(x->x.getCompteRendu().stream()).collect(Collectors.toList());
        List<CompteRenduList> compteRenduLists =  compteRendus.stream().map(compteRendu -> {
                    // change the value of comteRendu
                    CompteRenduList compteRenduList = new CompteRenduList();
                    if (compteRendu.getUser() != null)
                        compteRenduList.setUsername(compteRendu.getUser().getUsername());
                    if (compteRendu.getFile() != null)
                        compteRenduList.setFileId(compteRendu.getId());
                    if (compteRendu.getCour() != null)
                        compteRenduList.setCourname(compteRendu.getCour().getName());
                    if (compteRendu.getLocalDateTime() != null)
                        compteRenduList.setLocalDateTime(compteRendu.getLocalDateTime());
                    return compteRenduList;
                }).collect(Collectors.toList());
        return compteRenduLists;
       // step 4 : create list of user that add this compte rednu


        /*  Map<User,List<CompteRendu>> users = compteRendus.stream().collect(Collectors.groupingBy(CompteRendu::getUser));
        Map<UserResponse,List<CompteRenduList>> listMap = new HashMap<>();
        users.forEach((x,y)->{
            // step 4 change the user to user Id :
            UserResponse userResponse=modelMapper.map(x,UserResponse.class);

            // step 5 : change the list of compteRendu
            List<CompteRenduList> compteRenduLists =  y.stream().map(compteRendu -> {
                // change the value of comteRendu
                CompteRenduList compteRenduList = new CompteRenduList();
                if(compteRendu.getUser()!=null)
                    compteRenduList.setUsername(compteRendu.getUser().getUsername());
                if(compteRendu.getFile()!=null)
                    compteRenduList.setFileId(compteRendu.getId());
                if(compteRendu.getCour()!=null)
                    compteRenduList.setCourname(compteRendu.getCour().getName());
                if(compteRendu.getLocalDateTime()!=null)
                    compteRenduList.setLocalDateTime(compteRendu.getLocalDateTime());
                return compteRenduList;
            }).collect(Collectors.toList());
            listMap.put(userResponse,compteRenduLists);

        } );
        return listMap;*/
    }
}
