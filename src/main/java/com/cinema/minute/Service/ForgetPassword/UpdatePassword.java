package com.cinema.minute.Service.ForgetPassword;


import com.cinema.minute.Data.Entity.User;
import com.cinema.minute.Data.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UpdatePassword {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    public void updatePassword(String email, String code, String NewPassword) {
        User user = userRepository.findAll().stream().filter(users -> users.getEmail().equals(email))
                .filter(users -> users.getResetCode().equals(code))
                .findFirst()
                .orElseThrow(()-> new RuntimeException("this user does not exist"));

        // TODO : password encoder
       String pass =encoder.encode(NewPassword);
        user.setPassword(pass);
        userRepository.saveAndFlush(user);
    }
}
