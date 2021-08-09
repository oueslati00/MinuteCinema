package com.cinema.minute.Controllers;

import com.cinema.minute.Service.CommentService;
import com.cinema.minute.Service.UserService;
import com.cinema.minute.ui.Model.Request.Comments.CommentRequest;
import com.cinema.minute.ui.Model.Request.UserRequest.UserInformationRequest;
import com.cinema.minute.ui.Model.Response.UserResponse.UserResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@ApiOperation(value="api utiliser pour le gestion de user ")
@RestController
@RequestMapping("/api/")
public class User {

    private UserService userService;

    @Autowired
    public User(UserService userService){
        this.userService =userService;
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
}
