package ru.javalab.registration.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.javalab.registration.models.User;
import ru.javalab.registration.repositories.UsersRepository;

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
            usersRepository.confirmed(user.getUsername());
        }
    }

}
