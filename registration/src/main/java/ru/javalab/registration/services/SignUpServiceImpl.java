package ru.javalab.registration.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.javalab.registration.dto.SignUpDto;
import ru.javalab.registration.models.User;
import ru.javalab.registration.repositories.UsersRepository;



@Component
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    @Qualifier(value = "emailServiceImplWithPicture")
    private EmailService emailService;

    @Override
    public void signUpUser(SignUpDto signUpDto) {

        User user = User.builder()
                .username(signUpDto.getUsername())
                .email(signUpDto.getEmail())
                .password(signUpDto.getPassword())
                .isConfirmed(false)
                .build();
        user = emailService.sendMessage(user);
        System.out.println(user.getConfirmLink());
        usersRepository.save(user);
    }
}
