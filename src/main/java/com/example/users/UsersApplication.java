package com.example.users;

import com.example.users.domain.Role;
import com.example.users.domain.User;
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
    private void testJpaMethods() {
        Role admin = new Role("Администратор");
        roleRepository.save(admin);
        Role analyst = new Role("Аналитик");
        roleRepository.save(analyst);
        Role operator = new Role("Оператор");
        roleRepository.save(operator);


        User vasya = new User();
        vasya.setName("Вася");
        vasya.setLogin("VasyanXXX");
        vasya.setPassword("1234");
        Set<Role> vasyaRoles = new HashSet<>();
        vasyaRoles.add(admin);
        vasya.setRoles(vasyaRoles);
        userRepository.save(vasya);

        User petr = new User("Петя", "PGreat", "987");
        Set<Role> roles = new HashSet<>();
        roles.add(operator);
        roles.add(analyst);
        petr.setRoles(roles);
        userRepository.save(petr);

        userRepository.findAll().forEach(System.out::println);
    }

}
