package com.cinema.minute.Controllers;

import com.cinema.minute.Service.CommentService;
import com.cinema.minute.Service.MyResourceHttpRequestHandler;
import com.cinema.minute.Service.UserService;
import com.cinema.minute.ui.Model.Request.Comments.CommentRequest;
import com.cinema.minute.ui.Model.Request.UserRequest.UserInformationRequest;
import com.cinema.minute.ui.Model.Response.UserResponse.UserResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@ApiOperation(value="api utiliser pour le gestion de user ")
@RestController
@RequestMapping("/api/")
public class User {

    private UserService userService;
    private MyResourceHttpRequestHandler handler;

    @Autowired
    public User(UserService userService , MyResourceHttpRequestHandler handler){
        this.userService =userService;
        this.handler = handler;
    }

@ApiOperation(value="get list de utilisateur formateur et simple user ")
    @GetMapping("admin/simpleuser")
    public ResponseEntity<?> getListSimpleUser(){
        List<?> userList =userService.getListSimpleUser();
        return new ResponseEntity<>(userList,HttpStatus.OK);
    }


    @ApiOperation(value ="get information about chaque user  ")
    @GetMapping("user/informationUser/{id}")
    public ResponseEntity<?> InformationUser(@PathVariable long id ){
        UserResponse userResponse =userService.getInformation(id);
        return new ResponseEntity<>(userResponse,HttpStatus.OK);
    }
    @ApiOperation(value="get liste des formateur ")
    @GetMapping("admin/formateurList")
    public ResponseEntity<?> getFormateurUserList(){
        List<?> formateurlist =userService.getListFormateur();
        return new ResponseEntity<>(formateurlist,HttpStatus.OK);
    }
     @ApiOperation("supprimer l' utilisateur par son Id  ")
    @DeleteMapping("admin/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id){
        userService.removeUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("update information de cette utilsateur a travers son Id ")
    @PutMapping("user/Information/{id}")
    public ResponseEntity<?> updateInformation(@PathVariable long id , @RequestBody UserInformationRequest userInformationRequest ){
        userService.updateUser(userInformationRequest,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value="validation de user et donnees le role de formateur ")
    @GetMapping("admin/vFormateur/{id}")
    public ResponseEntity<?> validateFormateur(@PathVariable long id){
        userService.ValidateAsFormateur(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @ApiOperation(value="validation de user et donnee l'acces a etre un utilsateur normal ")
    @GetMapping("admin/vUser/{id}")
    public ResponseEntity<?> validateUser(@PathVariable long id){
        userService.ValidateUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(" suprimer le previllege de ce user  , l'utilsateur ne peut pas acceder a son compte , mais n'est pas suprimer  de base des donnees")
    @GetMapping("admin/user/Unvalidate/User/{id}")
    public ResponseEntity<?> UnvalidateUser(@PathVariable long id){
        userService.UnvalidateUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @ApiOperation("suprimer le previllege de formateur et le transfomer comme un utilsateur simple ")
    @GetMapping("admin/user/delete/Formateur/{id}")
    public ResponseEntity<?> DeleteFormateurRole(@PathVariable long id){
        userService.RemoveFormateurRole(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("ajouter une image a un user a travers son Id ")
    @PostMapping(value = "user/Image")
    public ResponseEntity<?> addimageByUserId(@RequestParam MultipartFile imageProfil , @RequestParam Long userId ) {
        userService.addImage(imageProfil,userId);
        return new ResponseEntity<>("this e=image was added correctly", HttpStatus.OK);
    }

    @ApiOperation(value = "display un image user par son Id   ")
    @GetMapping("user/informationUser/image/{id}")
    public void byterange (HttpServletRequest request, HttpServletResponse response, @PathVariable Long id ) throws ServletException, IOException {
        File f = userService.getImageByUserId(id);
        if(f.exists()){
            System.out.println("ok ");
        }
        request.setAttribute(MyResourceHttpRequestHandler.ATTR_FILE, f);
        handler.handleRequest(request, response);
    }
}
