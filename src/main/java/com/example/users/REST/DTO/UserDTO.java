package com.example.users.REST.DTO;

import com.example.users.entities.User;

import java.util.Set;
import java.util.stream.Collectors;

public class UserDTO extends UserNoRolesDTO {
    public Set<RoleDTO> roles;

    public UserDTO() {
    }

    public UserDTO(User user) {
        super(user);
        this.roles = user.getRoles().stream().map(RoleDTO::new).collect(Collectors.toSet());
    }
}
