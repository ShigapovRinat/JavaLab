package ru.javalab.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.javalab.security.dto.SignUpDto;
import ru.javalab.security.models.User;
import ru.javalab.security.repositories.UsersRepository;

import java.util.UUID;


@Component
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void signUp(SignUpDto signUpDto) {

        if (!usersRepository.find(signUpDto.getEmail()).isPresent()) {
            User user = User.builder()
                    .name(signUpDto.getName())
                    .email(signUpDto.getEmail())
                    .hashPassword(passwordEncoder.encode(signUpDto.getPassword()))
                    .isConfirmed(false)
                    .build();
            String confirmLink = UUID.randomUUID().toString();
            user.setConfirmLink(confirmLink);
            usersRepository.save(user);
        } else throw new IllegalArgumentException("This email already has");
    }
}
