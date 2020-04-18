package ru.javalab.chatWebSocket.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.javalab.chatWebSocket.dto.SignInDto;
import ru.javalab.chatWebSocket.dto.TokenDto;
import ru.javalab.chatWebSocket.models.User;
import ru.javalab.chatWebSocket.repositories.UsersRepository;

import java.util.Optional;

@Component
public class SignInServiceImpl implements SignInService {

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public TokenDto signIn(SignInDto signInDto) {
        Optional<User> optionalUser = usersRepository.findByLogin(signInDto.getLogin());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(signInDto.getPassword(), user.getHashPassword())) {
                String token = Jwts.builder()
                        .claim("id", user.getId().toString())
                        .claim("login", user.getLogin())
                        .signWith(SignatureAlgorithm.HS256, secret)
                        .compact();
                return new TokenDto(token);
            } else throw new IllegalArgumentException("Wrong email/hashPassword");
        } else throw new IllegalArgumentException("Person not found");
    }
}
