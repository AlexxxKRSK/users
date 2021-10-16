package com.example.users;

import com.example.users.domain.Role;
import com.example.users.domain.User;
import com.example.users.domain.UserWithoutRoles;
import com.example.users.repositories.RoleRepository;
import com.example.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/user")
public class UsersController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping(path = "/all")
    @ResponseBody
    public Iterable<UserWithoutRoles> getAllUsers() {
        return userRepository.findAllWithoutRoles();
    }

    @GetMapping(path = "/{login}")
    @ResponseBody
    public User getUser(@PathVariable String login) {
        return userRepository.getByLogin(login);
    }

    @GetMapping(path = "/{login}/roles")
    @ResponseBody
    public Iterable<Role> getUserRoles(@PathVariable String login) {
        return userRepository.getByLogin(login).getRoles();
    }

    @DeleteMapping(path = "/{login}")
    public ResponseEntity<Void> deleteUser(@PathVariable String login) {
        User user = userRepository.getByLogin(login);
        System.out.println(user);
        if (user != null)
        {
            userRepository.delete(user);
        }
        return ResponseEntity.ok().build();
    }

}
