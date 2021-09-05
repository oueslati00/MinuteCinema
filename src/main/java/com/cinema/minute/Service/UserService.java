package com.cinema.minute.Service;

import com.cinema.minute.Data.Entity.*;
import com.cinema.minute.Data.Repository.*;
import com.cinema.minute.Service.UploadFile.StorageService;
import com.cinema.minute.ui.Model.Request.UserRequest.UserInformationRequest;
import com.cinema.minute.ui.Model.Response.InfoResponse;
import com.cinema.minute.ui.Model.Response.UserResponse.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private RoleRepository roleRepository;
    private FormationRepo formationRepo;
    private CommentRepository commentRepository;
    private CompteRenduRepo compteRenduRepo;
    private StorageService storageService;
    @Autowired
    private VideoDkikaRepo videoDkikaRepo;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper, RoleRepository roleRepository , FormationRepo formationRepo , CommentRepository commentRepository , CompteRenduRepo compteRenduRepo, StorageService storageService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
        this.formationRepo = formationRepo;
        this.commentRepository=commentRepository;
        this.compteRenduRepo = compteRenduRepo;
        this.storageService = storageService;
    }




    public List<?> getListSimpleUser() {
        return userRepository.findAll().stream().filter(user -> {
               boolean isUser=  user.getRoles().stream().anyMatch(x->(x.getName().equals(ERole.ROLE_USER)) || x.getName().equals(ERole.ROLE_MODERATOR));
             //  boolean length = user.getRoles().size() == 1;
           return isUser ;
        }).map(user -> {
            // map user to responseUser
            UserResponse userResponse = modelMapper.map(user,UserResponse.class);
            return userResponse;
        }).collect(Collectors.toList());
    }


    public UserResponse getInformation(long id ) {
        User user=userRepository.findById(id).orElseThrow(()-> new RuntimeException("this user does not exist"));
        UserResponse userResponse=modelMapper.map(user,UserResponse.class);
        return userResponse;
    }


    public List<?> getListFormateur() {
        return userRepository.findAll().stream().filter(user -> {
            boolean isUser=  user.getRoles().stream().anyMatch(x->x.getName().equals(ERole.ROLE_MODERATOR));
          //  boolean length = (user.getRoles().size() == 2);
            return  isUser ;
        }).map(user -> {
            UserResponse userResponse = modelMapper.map(user,UserResponse.class);
            return userResponse;
        }).collect(Collectors.toList());
    }

    public void removeUserById(long id) {
     User user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("this user does not exist"));
     user.getFormations().stream().forEach(x->x.setUser(null));
     user.setRoles(null);
     user.getComments().stream().forEach(x->x.setUser(null));
     user.getCompteRendu().stream().forEach(x->x.setUser(null));

     formationRepo.saveAllAndFlush(user.getFormations());
     compteRenduRepo.saveAllAndFlush(user.getCompteRendu());
     commentRepository.saveAllAndFlush(user.getComments());

      userRepository.saveAndFlush(user);
      userRepository.deleteById(id);

      // use delete by username insted
    }

    public void updateUser(UserInformationRequest userInformationRequest, long id) {
       User user= userRepository.findById(id).orElseThrow(()-> new RuntimeException("this user does not exist"));
       User user1 = modelMapper.map(userInformationRequest,User.class);
       user1.setRoles(user.getRoles());
       user1.setPassword(user.getPassword());
       user1.setId(user.getId());
       user1.setImageUser(user.getImageUser());
       user1.setAccountVerfied(true);
       userRepository.save(user1);
       userRepository.flush();
    }

    public void ValidateAsFormateur(long id) {
        User user= userRepository.findById(id).orElseThrow(()-> new RuntimeException("this user does not exist"));
        Role role = roleRepository.findByName(ERole.ROLE_MODERATOR).get();
        user.getRoles().add(role);
        user.setAccountVerfied(true);
        userRepository.save(user);

    }

    public void ValidateUser(long id) {
        User user= userRepository.findById(id).orElseThrow(()-> new RuntimeException("this user does not exist"));
        Role role = roleRepository.findByName(ERole.ROLE_USER).get();
        user.getRoles().add(role);
        user.setAccountVerfied(true);
        userRepository.save(user);
        System.out.println(user);
    }

    public void UnvalidateUser(long id){
        User user= userRepository.findById(id).orElseThrow(()-> new RuntimeException("this user does not exist"));
        user.setAccountVerfied(false);
        userRepository.save(user);
    }

    public void RemoveFormateurRole(long id){
        User user= userRepository.findById(id).orElseThrow(()-> new RuntimeException("this user does not exist"));
        Role role = roleRepository.findByName(ERole.ROLE_MODERATOR).get();
        user.getRoles().remove(role);
          userRepository.save(user);
    }

    public void addImage(MultipartFile imageProfil, Long userId) {
        User user= userRepository.findById(userId).orElseThrow(()-> new RuntimeException("this user does not exist"));
        UploadFile f = storageService.save(imageProfil);
        user.setImageUser(f);
        userRepository.saveAndFlush(user);

    }

    public File getImageByUserId(Long id) throws FileNotFoundException {
        User user= userRepository.findById(id).orElseThrow(()-> new RuntimeException("this user does not exist"));
         if(user.getImageUser() != null && user.getImageUser().getUrlFile() != null){
             return new File(user.getImageUser().getUrlFile());
         }
        throw new FileNotFoundException("this file does not exist");
    }

    public InfoResponse getInforStatiscitque() {
        InfoResponse infor = new InfoResponse();
        infor.setFormationNumber(formationRepo.count());
        infor.setCompteRenduNumber(compteRenduRepo.count());
        infor.setVideoNumber(videoDkikaRepo.count());
        return infor;
    }
}
