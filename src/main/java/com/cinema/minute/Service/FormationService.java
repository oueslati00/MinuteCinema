package com.cinema.minute.Service;

import com.cinema.minute.Data.Entity.*;
import com.cinema.minute.Data.Repository.*;
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
import java.time.LocalDate;
import java.time.Period;
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
    private CommentRepository commentRepository;
    @Autowired
    private CompteRenduRepo compteRenduRepo;

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
        // test if the formateur was exist

       System.out.println(formationRequest.getFormateurId());
       User user =userRepository.findById(formationRequest.getFormateurId().longValue()).orElseThrow(()-> new RuntimeException("this user does not exist"));
       if(user==null) {
            throw new NullPointerException("this user does not exist");
        }

         Formation formation =   modelMapper.map(formationRequest, Formation.class);

        formation.setUser(user);
        // this line is add to resolve the problem of formateur Id that was interepted as formationId by ModelMapper
        formation.setId(null);

       Formation formation1= formationRepo.save(formation);
       // save list of chapter
       for (int i = 0; i < formation.getChapter().size() ; i++) {
           formation.getChapter().get(i).setFormation(formation);
       }
       chapterRepo.saveAll(formation.getChapter());

       for (int i = 0; i <formation.getChapter().size() ; i++) {
           for (int j = 0; j < formation.getChapter().get(i).getCour().size() ; j++) {
               // set chapter to cour object
               formation.getChapter().get(i).getCour().get(j).setChapter( formation.getChapter().get(i));
               // set video id to cour from formation request
               UploadFile file = storageService.fileInDB.getFileById(formationRequest.getChapter().get(i).getCour().get(j).getVid());
               formation.getChapter().get(i).getCour().get(j).setVideo(file);

           }
           courRepo.saveAll(formation.getChapter().get(i).getCour());
       }
       Integer id =  formation1.getId();
       Formation formation2 = formationRepo.getById(id);
       formationResponse formationResponse=modelMapper.map(formation2,formationResponse.class);
      return formationResponse;

    }

    public Integer AddVideo(MultipartFile file){
        UploadFile f = storageService.save(file);
        return f.getId();
    }

    public formationResponse getFormationDescription(Integer id){
        Formation formation= formationRepo.findById(id).orElseThrow(()-> new RuntimeException("this formation does not exist"));
        formationResponse formationResponse = modelMapper.map(formation, formationResponse.class);
        long dur = calculeDur(formation.getFirstDate(),formation.getFinalDate());
        formationResponse.setDurationPerNow(dur);
        return formationResponse;
    }

    private long calculeDur(LocalDate firstDate, LocalDate finalDate) {
      long l = finalDate.toEpochDay()- firstDate.toEpochDay();
      long l1 = LocalDate.now().toEpochDay()-firstDate.toEpochDay();

        return Math.round(l1*100.00/l);

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
               formationListInfo.setFormateurId(formation.getUser().getId());

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

    public List<?> getListFormationByformateurId(Long id) {
       return formationRepo.findAll().stream().filter(x-> x.getUser().getId().equals(id))
                .collect(Collectors.toList());
    }

    public boolean removeFormationById(Long id) {

        Formation formation =formationRepo.findById(id.intValue()).orElseThrow(() -> new RuntimeException("this formation does not exist "));
        List<Chapter> chapter = formation.getChapter();
        List<Cours> cours =formation.getChapter().stream().flatMap(x->x.getCour().stream()).collect(Collectors.toList());

        List<Comment> comment = cours.stream().flatMap(x->x.getComments().stream()).collect(Collectors.toList());
        commentRepository.deleteAll(comment);
        List<UploadFile> listVideo = cours.stream().map(x->x.getVideo()).collect(Collectors.toList());
        List<CompteRendu> compteRendu = cours.stream().flatMap(x->x.getCompteRendu().stream()).collect(Collectors.toList());
        compteRendu.stream().forEach(x->compteRenduRepo.delete(x));
        compteRendu.stream().forEach(x-> storageService.deleteById(x.getFile().getId()));
        courRepo.deleteAll(cours);
        listVideo.stream().forEach(x-> storageService.deleteById(x.getId()));
        chapterRepo.deleteAll(chapter);
        formationRepo.deleteById(id.intValue());

    return true;
    }
}
