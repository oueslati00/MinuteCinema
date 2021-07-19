package com.cinema.minute.Service.ForgetPassword;


import com.cinema.minute.Data.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    CodeCrud codeCrud;

    @Autowired
    GeneratePassword generatePassword;
    @Autowired
    UserRepository userRepository;

    @Autowired
    private JavaMailSender emailsender;

    public boolean validateMail(String mail){
        return  userRepository.findAll().stream().anyMatch(users -> users.getEmail().equals(mail));
    }

    public boolean sendMail(String email){
        validateMail(email);
        String password = generatePassword.createPassword();
        codeCrud.AddCode(password,email);
        removeCode(email);
        SimpleMailMessage message = createMail(email,password);
        emailsender.send(message);
        return true;
    }

    private SimpleMailMessage createMail(String email, String password){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("dkikacinema@gmail.com");
        message.setTo(email);
        message.setSubject("reset password");
        message.setText( "If you requested a password reset for your account of d9i9aCinema ,\n "
                + "use the confirmation code below to complete the process.\n"
                + "If you didn't make this request, ignore this email.\n "
                + " your code is :  " + password);
        return message;

    }


    public void validateResetCode(String email, String code) {
        validateMail(email);
        codeCrud.VerifierCode(email,code);
    }

    private void removeCode(String email){
        new Thread(()->{
            try {

                Thread.sleep(1_800_000L); // 30min
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            codeCrud.removeCode(email);

            System.out.println("the 20 second was ended \nand the code was removed");
        }).start();
    }


}
