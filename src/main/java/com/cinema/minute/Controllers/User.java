package com.cinema.minute.Controllers;

import com.cinema.minute.Service.CommentService;
import com.cinema.minute.Service.UserService;
import com.cinema.minute.ui.Model.Request.Comments.CommentRequest;
import com.cinema.minute.ui.Model.Request.UserRequest.UserInformationRequest;
import com.cinema.minute.ui.Model.Response.UserResponse.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class User {

    private UserService userService;

    @Autowired
    public User(UserService userService){
        this.userService =userService;
    }


    @GetMapping("admin/simpleuser")
    public ResponseEntity<?> getListSimpleUser(){
        List<?> userList =userService.getListSimpleUser();
        return new ResponseEntity<>(userList,HttpStatus.OK);
    }

    @GetMapping("admin/informationUser/{id}")
    public ResponseEntity<?> InformationUser(@PathVariable long id ){
        UserResponse userResponse =userService.getInformation(id);
        return new ResponseEntity<>(userResponse,HttpStatus.OK);
    }
    @GetMapping("admin/formateurList")
    public ResponseEntity<?> getFormateurUserList(){
        List<?> formateurlist =userService.getListFormateur();
        return new ResponseEntity<>(formateurlist,HttpStatus.OK);
    }

    @DeleteMapping("admin/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id){
        userService.removeUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("user/Information/{id}")
    public ResponseEntity<?> updateInformation(@PathVariable long id , @RequestBody UserInformationRequest userInformationRequest ){
        userService.updateUser(userInformationRequest,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("admin/vFormateur/{id}")
    public ResponseEntity<?> validateFormateur(@PathVariable long id){
        userService.ValidateAsFormateur(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("admin/vUser/{id}")
    public ResponseEntity<?> validateUser(@PathVariable long id){
        userService.ValidateUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("admin/user/Unvalidate/User/{id}")
    public ResponseEntity<?> UnvalidateUser(@PathVariable long id){
        userService.UnvalidateUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("admin/user/delete/Formateur/{id}")
    public ResponseEntity<?> DeleteFormateurRole(@PathVariable long id){
        userService.RemoveFormateurRole(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
