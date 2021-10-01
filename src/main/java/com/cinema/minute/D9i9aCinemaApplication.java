package com.cinema.minute;


import com.cinema.minute.Data.Entity.ERole;
import com.cinema.minute.Data.Entity.Role;
import com.cinema.minute.Data.Repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class D9i9aCinemaApplication {

    public static void main(String[] args) {
        SpringApplication.run(D9i9aCinemaApplication.class, args);
    }

   /* @Bean
    CommandLineRunner start(RoleRepository role){
        return args -> {
            Role userRole = new Role(ERole.ROLE_USER);
            Role test =role.save(userRole);
            System.out.println(test.getName());
            Role adminRole = new Role(ERole.ROLE_ADMIN);
            role.save(adminRole);
            Role FormateurRole = new Role(ERole.ROLE_MODERATOR);
            role.save(FormateurRole);
        };

    }
*/

}
