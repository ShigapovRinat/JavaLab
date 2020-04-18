package ru.javalab.chatWebSocket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.javalab.chatWebSocket.dto.SignUpDto;
import ru.javalab.chatWebSocket.models.User;
import ru.javalab.chatWebSocket.repositories.UsersRepository;

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
