package ru.javalab.registration.repositories;

import ru.javalab.registration.models.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<String, User>{
    Optional<User> findByConfirmLink(String confirmLink);
    void confirmed(String username);
}
