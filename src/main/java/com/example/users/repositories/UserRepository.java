package com.example.users.repositories;

import com.example.users.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

    User getByLogin(String login);

}
