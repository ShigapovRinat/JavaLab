package ru.javalab.security.repositories;

import ru.javalab.security.models.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<String, User>{
    Optional<User> findByConfirmLink(String confirmLink);
    void confirmed(String username);
}
