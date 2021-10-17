package com.example.users.repositories;

import com.example.users.entities.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    boolean existsByName(String name);
    Role getByName(String name);
}
