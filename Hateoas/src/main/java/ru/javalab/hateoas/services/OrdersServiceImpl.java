package ru.javalab.hateoas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javalab.hateoas.models.Good;
import ru.javalab.hateoas.models.Order;
import ru.javalab.hateoas.models.User;
import ru.javalab.hateoas.reposiories.BasketsRepository;
import ru.javalab.hateoas.reposiories.OrdersRepository;
import ru.javalab.hateoas.reposiories.UsersRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private BasketsRepository basketsRepository;

    @Autowired
    private UsersRepository usersRepository;


    @Override
    @Transactional
    public Order createOrder(Long userId) {

        User user = usersRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        Good good = user.getBasket().getGood();
        basketsRepository.delete(user.getBasket());
        return ordersRepository.save(Order.builder()
                .orderId(UUID.randomUUID().toString())
                .good(good)
                .user(user)
                .quantityGood(user.getBasket().getQuantityGood())
                .build()
        );
    }
}
