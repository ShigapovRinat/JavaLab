package ru.javalab.chat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.javalab.chat.dto.SignUpDto;
import ru.javalab.chat.models.User;
import ru.javalab.chat.repositories.UsersRepository;

@Component
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void signUp(SignUpDto signUpDto) {
        usersRepository.save(User.builder()
                .login(signUpDto.getLogin())
                .hashPassword(passwordEncoder.encode(signUpDto.getPassword()))
                .build()
        );

    }
}
