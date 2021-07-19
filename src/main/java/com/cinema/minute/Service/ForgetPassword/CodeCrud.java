package com.cinema.minute.Service.ForgetPassword;


import com.cinema.minute.Data.Entity.User;
import com.cinema.minute.Data.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CodeCrud {

    @Autowired
    UserRepository userRepository;

    public void AddCode(String code, String email){
        User user = userRepository.findAll().stream().filter(users -> users.getEmail().equals(email))
                .findFirst().orElseThrow(()-> new RuntimeException("user not found exception"));
        user.setResetCode(code);
        userRepository.saveAndFlush(user);
    }

    public User removeCode(String email){
        User user = userRepository.findAll().stream().filter(users -> users.getEmail().equals(email))
                .findFirst().get();
        user.setResetCode(null);
        userRepository.saveAndFlush(user);
        return user;
    }
    public User VerifierCode(String email,String code){
       return userRepository.findAll().stream().filter(users -> users.getEmail().equals(email))
                .filter(users -> users.getResetCode().equals(code))
                .findFirst()
                .orElseThrow(()-> new RuntimeException(" this code is not valid "));
    }


}
