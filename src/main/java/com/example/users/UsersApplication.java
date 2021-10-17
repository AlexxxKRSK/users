package com.example.users;

import com.example.users.entities.Role;
import com.example.users.entities.User;
import com.example.users.repositories.RoleRepository;
import com.example.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class UsersApplication {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(UsersApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    private void setInitialValues() {
        String adminName = "Администратор";
        Role admin = null;
        if (!roleRepository.existsByName(adminName)) {
            admin = new Role(adminName);
            roleRepository.save(admin);
        } else admin = roleRepository.getByName(adminName);

        Role operator = null;
        String operatorName = "Оператор";
        if (!roleRepository.existsByName(operatorName)) {
            operator = new Role(operatorName);
            roleRepository.save(operator);
        } else operator = roleRepository.getByName(operatorName);

        Role analyst = null;
        String analystName = "Аналитик";
        if (!roleRepository.existsByName(analystName)) {
            analyst = new Role(analystName);
            roleRepository.save(analyst);
        } else analyst = roleRepository.getByName(analystName);

        User vasya = new User();
        vasya.setName("Вася");
        vasya.setLogin("VasyanXXX");
        vasya.setPassword("1234");
        vasya.addRole(admin);
        userRepository.save(vasya);

        User petr = new User("Петя", "PGreat", "987");
        Set<Role> roles = new HashSet<>();
        roles.add(operator);
        roles.add(analyst);
        petr.setRoles(roles);
        userRepository.save(petr);


    }

}
