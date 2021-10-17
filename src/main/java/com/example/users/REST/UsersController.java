package com.example.users.REST;

import com.example.users.REST.DTO.*;
import com.example.users.entities.User;
import com.example.users.repositories.RoleRepository;
import com.example.users.repositories.UserRepository;
import com.example.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/user")
public class UsersController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    UserService userService;

    @GetMapping(path = "/all")
    @ResponseBody
    public List<UserNoRolesDTO> getAllUsers() {
        Iterable<User> users = userService.getAllUsers();
        List<UserNoRolesDTO> usersDTO = new ArrayList<>();
        users.forEach(user -> usersDTO.add(new UserNoRolesDTO(user)));
        return usersDTO;
    }

    @GetMapping(path = "/{login}")
    @ResponseBody
    public UserDTO getUser(@PathVariable String login) {
        User user = userService.getUser(login);
        return user == null ? new UserDTO() : new UserDTO(user);
    }

    @DeleteMapping(path = "/{login}/remove")
    public ResponseEntity<Void> deleteUser(@PathVariable String login) {
        userService.deleteUser(login);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/create")
    @ResponseBody
    public Response addUser(@RequestBody UserDTO userDTO) {
        List<String> errors = userService.addUser(userDTO);
        return errors.isEmpty() ? new ResponseOk() : new ResponseFailed(errors);
    }

    @PutMapping(path = "/{login}/edit")
    @ResponseBody
    public Response editUser(@PathVariable String login, @RequestBody UserDTO userDTO) {
        List<String> errors = userService.editUser(login, userDTO);
        return errors.isEmpty() ? new ResponseOk() : new ResponseFailed(errors);
    }

}
