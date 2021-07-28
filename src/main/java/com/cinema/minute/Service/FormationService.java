package com.cinema.minute.Service;

import com.cinema.minute.Data.Entity.*;
import com.cinema.minute.Data.Repository.ChapterRepo;
import com.cinema.minute.Data.Repository.CourRepo;
import com.cinema.minute.Data.Repository.FormationRepo;
import com.cinema.minute.Data.Repository.UserRepository;
import com.cinema.minute.Service.UploadFile.StorageService;
import com.cinema.minute.ui.Model.Request.FormationRequests.CoursRequest;
import com.cinema.minute.ui.Model.Request.FormationRequests.FormationRequest;
import com.cinema.minute.ui.Model.Response.FormationResponse.FormationListInfo;
import com.cinema.minute.ui.Model.Response.FormationResponse.formationResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class FormationService {

    private CourRepo courRepo;
    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private FormationRepo formationRepo;
    private ChapterRepo chapterRepo;
    private StorageService storageService;

    @Autowired
    public FormationService(CourRepo courRepo, ModelMapper modelMapper , UserRepository userRepository, FormationRepo formationRepo, ChapterRepo chapterRepo, StorageService storageService){
        this.formationRepo = formationRepo;
        this.courRepo = courRepo;
        this.modelMapper = modelMapper;
        this.userRepository=userRepository;
        this.chapterRepo = chapterRepo;
        this.storageService = storageService;
    }




   public formationResponse AddFormation(FormationRequest formationRequest) {
        System.out.println(formationRequest);
       User user =userRepository.findById(formationRequest.getFormateurId().longValue()).orElseThrow(()-> new RuntimeException("this user does not exist"));
       if(user==null) {
            throw new NullPointerException("this user does not exist");
        }

         Formation formation =   modelMapper.map(formationRequest, Formation.class);

        formation.setUser(user);
        // this line is add to resolve the problem of formateur Id that was interepted as formationId by ModelMapper
        formation.setId(null);

       Formation formation1= formationRepo.save(formation);
       for (int i = 0; i < formation.getChapter().size() ; i++) {
           formation.getChapter().get(i).setFormation(formation);
       }
       chapterRepo.saveAll(formation.getChapter());

       for (int i = 0; i <formation.getChapter().size() ; i++) {
           for (int j = 0; j < formation.getChapter().get(i).getCours().size() ; j++) {
               formation.getChapter().get(i).getCours().get(j).setChapter( formation.getChapter().get(i));
           }
           courRepo.saveAll(formation.getChapter().get(i).getCours());
       }
       Integer id =  formation1.getId();
       Formation formation2 = formationRepo.getById(id);
       formationResponse formationResponse=modelMapper.map(formation2,formationResponse.class);
      return formationResponse;

    }

    public void AddVideo(MultipartFile file,Long CourId){
        Cours cours = courRepo.findById(CourId).orElseThrow(()-> new RuntimeException(" this cours does not exist"));
        UploadFile f = storageService.save(file);
        cours.setVideo(f);

        courRepo.saveAndFlush(cours);
    }

    public formationResponse getFormationDescription(Integer id){
        Formation formation= formationRepo.findById(id).orElseThrow(()-> new RuntimeException("this formation does not exist"));
        formationResponse formationResponse = modelMapper.map(formation, formationResponse.class);
        return formationResponse;
    }

    public List<FormationListInfo> getFormationListInformation(){
       return formationRepo.findAll().stream().map(formation -> {

           FormationListInfo formationListInfo = null;
           try {

                formationListInfo = new FormationListInfo();
                formationListInfo.setId(formation.getId());
               formationListInfo.setFormationName(formation.getName());
               formationListInfo.setDateToStart(formation.getFirstDate());
               formationListInfo.setFormateurName(formation.getUser().getUsername());

           }catch (NullPointerException e){
           }
           return formationListInfo;
        }).collect(Collectors.toList());
    }

    public File getFormationvideoById(long param) {
        //storageService.load()
      return null;
    }

    public File getResource(Integer id) {
        // get video By Id cours
         Cours cours = courRepo.findById(id.longValue()).orElseThrow(()-> new RuntimeException("this cours does not exist "));
         if(cours.getVideo() == null)
             throw new RuntimeException("this video does not exist");
       String filePath=  cours.getVideo().getUrlFile();
         File f = new File(filePath);
         return f;
    }
}
