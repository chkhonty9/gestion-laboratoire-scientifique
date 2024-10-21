package org.cn.userservice;

import org.cn.userservice.configuration.RSAConfig;
import org.cn.userservice.entity.Enseignant;
import org.cn.userservice.service.EnseignantService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties(RSAConfig.class)
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(EnseignantService enseignantService) {
        return args -> {
            Enseignant enseignant = new Enseignant();
            enseignant.setId(null);
            enseignant.setNom("CHKHONTY");
            enseignant.setPrenom("NOUHAILA");
            enseignant.setEmail("chkhonty@gmail.com");
            enseignant.setRole("ENSEIGNANT");
            enseignant.setPassword("nouhaila");
            enseignant.setCne("LB237777");
            enseignant.setThematicResearch("thematic research");
            enseignantService.save(enseignant);
            System.out.println("- enseignant : " + enseignant);
        };
    }

    @Bean
    PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();
    };

}
