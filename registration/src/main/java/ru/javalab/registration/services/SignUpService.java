package ru.javalab.registration.services;

import ru.javalab.registration.dto.SignUpDto;
import ru.javalab.registration.models.User;

public interface SignUpService {
    void signUpUser(SignUpDto signUpDto);
}
