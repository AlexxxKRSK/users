package com.example.users.services;

import com.example.users.REST.DTO.UserDTO;
import com.example.users.entities.Role;
import com.example.users.entities.User;
import com.example.users.repositories.RoleRepository;
import com.example.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(String login) {
        return userRepository.getByLogin(login);
    }

    public boolean deleteUser(String login) {
        User user = userRepository.getByLogin(login);
        if (user == null) {
            return false;
        }
        userRepository.delete(user);
        return true;
    }

    public List<String> addUser(UserDTO userDTO) {
        List<String> errors = validateUserDTO(userDTO);
        if (userRepository.existsById(userDTO.login)) {
            errors.add(String.format("Пользователь с логином %s уже существует", userDTO.login));
        }
        if (errors.size() == 0) {
            User user = new User(userDTO);
            setRoles(userDTO, user);
            userRepository.save(user);
        }
        return errors;
    }

    public List<String> editUser(String login, UserDTO userDTO) {
        List<String> errors = validateUserDTO(userDTO);
        User editedUser = userRepository.getByLogin(login);
        if (editedUser == null) {
            errors.add(String.format("Пользователя с логином %s не существует", login));
        }

        if (errors.size() == 0) {
            if (!editedUser.getLogin().equals(userDTO.login)) {
                errors.add("Запрещено изменять логин, тк первичный ключ");
                return errors;
            }
            if (!editedUser.getName().equals(userDTO.name)) {
                editedUser.setName(userDTO.name);
            }
            if (!editedUser.getPassword().equals(userDTO.password)) {
                editedUser.setPassword(userDTO.password);
            }
            setRoles(userDTO, editedUser);

            userRepository.save(editedUser);
        }
        return errors;
    }

    private void setRoles(UserDTO userDTO, User user) {
        if (userDTO.roles != null) {
            Set<Role> roles = userDTO.roles.stream().map(roleDTO -> roleRepository.findById(roleDTO.id).get()).collect(Collectors.toSet());
            user.setRoles(roles);
        }
    }

    private List<String> validateUserDTO(UserDTO userDTO) {
        List<String> errors = new ArrayList<>();

        if (userDTO.name == null || userDTO.name.isEmpty()) {
            errors.add("Поле 'Имя' не может быть пустым");
        }
        if (userDTO.login == null || userDTO.login.isEmpty()) {
            errors.add("Поле 'Логин' не может быть пустым");
        }
        if (userDTO.password == null || userDTO.password.isEmpty()) {
            errors.add("Поле 'Пароль' не может быть пустым");
        } else {
            if (!userDTO.password.matches(".*\\d+.*")) {
                errors.add("Пароль должен содержать минимум одну цифру");
            }
            if (userDTO.password.equals(userDTO.password.toLowerCase())) {
                errors.add("Пароль должен содержать минимум одну букву в заглавном регистре");
            }
        }
        if (userDTO.roles != null) {
            userDTO.roles.forEach(roleDTO -> {
                if (!roleRepository.existsById(roleDTO.id)) {
                    errors.add(String.format("Роль %s %s не существует", roleDTO.id, roleDTO.name));
                }
            });
        }
        return errors;
    }
}
