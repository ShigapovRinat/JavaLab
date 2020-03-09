package ru.javalab.registration.services;

import ru.javalab.registration.dto.SignUpDto;

public interface SignUpService {
    void signUpUser(SignUpDto signUpDto);
}
