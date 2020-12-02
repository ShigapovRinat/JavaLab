package ru.javalab.hateoas.services;

import ru.javalab.hateoas.models.Order;

public interface OrdersService {

    Order createOrder(Long userId);
}
