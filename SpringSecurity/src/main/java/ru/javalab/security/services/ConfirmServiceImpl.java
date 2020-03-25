package ru.javalab.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.javalab.security.models.User;
import ru.javalab.security.repositories.UsersRepository;

import java.util.Optional;

@Component
public class ConfirmServiceImpl implements ConfirmService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public void isConfirmed(String confirmLink) {
        Optional<User> userOptional = usersRepository.findByConfirmLink(confirmLink);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setConfirmed(true);
            usersRepository.confirmed(user.getName());
        }
    }

}
