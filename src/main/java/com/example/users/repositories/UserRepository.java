package com.example.users.repositories;

import com.example.users.domain.User;
import com.example.users.domain.UserWithoutRoles;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, String> {

    User getByLogin(String login);

    @Query(value = "select u.name as name, u.login as login, u.password as password from users u")
    List<UserWithoutRoles> findAllWithoutRoles();
}
