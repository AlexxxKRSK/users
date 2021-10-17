package com.example.users.REST.DTO;

import com.example.users.entities.Role;

public class RoleDTO {
    public Integer id;
    public String name;

    public RoleDTO() {
    }

    public RoleDTO(Role role) {
        this.id = role.getId();
        this.name = role.getName();
    }
}
