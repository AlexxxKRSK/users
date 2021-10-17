package com.example.users.REST.DTO;

import com.example.users.entities.User;

public class UserNoRolesDTO {
    public String name;
    public String login;
    public String password;

    public UserNoRolesDTO() {
    }

    public UserNoRolesDTO(User user) {
        this.name = user.getName();
        this.login = user.getLogin();
        this.password = user.getPassword();
    }
}
