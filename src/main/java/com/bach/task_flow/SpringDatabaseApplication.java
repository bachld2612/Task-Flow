package com.bach.task_flow;

import com.bach.task_flow.domains.User;
import com.bach.task_flow.enums.Role;
import com.bach.task_flow.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringDatabaseApplication {

    private static final Logger log = LoggerFactory.getLogger(SpringDatabaseApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringDatabaseApplication.class, args);
    }

    @Bean
    public CommandLineRunner createAdminUser(UserRepository userRepository) {
        return args -> {
            if(userRepository.findByUsername("admin").isEmpty()){
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
                User user = new User();
                user.setEnabled(true);
                user.setUsername("admin");
                user.setPassword(passwordEncoder.encode("admin123"));
                user.setRole(Role.ADMIN);
                userRepository.save(user);
                log.info("Admin user created");
            }
        };
    }

}
