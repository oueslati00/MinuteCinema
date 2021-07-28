package com.cinema.minute.Controllers;

import com.cinema.minute.Service.ForgetPassword.MailService;
import com.cinema.minute.Service.ForgetPassword.UpdatePassword;
import com.cinema.minute.ui.Model.Request.ForgetPassword.RequestForgetPassword;
import com.cinema.minute.ui.Model.Request.ForgetPassword.RequestValidateResetCode;
import com.cinema.minute.ui.Model.Request.ForgetPassword.UpdatePasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth/forget")
public class restController {

    @Autowired
    MailService mservice;

    @Autowired
    UpdatePassword updatePassword;
    @PostMapping("/sendcode")
    public ResponseEntity<?> sendCode(@Valid @RequestBody RequestForgetPassword requestForgetPassword){
        mservice.sendMail(requestForgetPassword.getEmail());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/validateCode")
    public ResponseEntity<?> validateCode(@Valid @RequestBody RequestValidateResetCode requestValidateResetCode){
        String email = requestValidateResetCode.getEmail();
        String code = requestValidateResetCode.getCode();
        mservice.validateResetCode(email,code);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("UpdatePassword")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest){
        String password = updatePasswordRequest.getPassword();
        String code = updatePasswordRequest.getCode();
        String email = updatePasswordRequest.getEmail();
        updatePassword.updatePassword(email,code,password);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
