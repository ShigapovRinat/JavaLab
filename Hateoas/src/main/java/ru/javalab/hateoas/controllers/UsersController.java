package ru.javalab.hateoas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.javalab.hateoas.services.OrdersService;

@RestController
public class UsersController {

    @Autowired
    private OrdersService ordersService;

    @RequestMapping(value = "/user/{user-id}/order-create", method = RequestMethod.PUT)
    public @ResponseBody
    ResponseEntity<?> createOrder(@PathVariable("user-id") Long userId) {
        return ResponseEntity.ok(
                EntityModel.of(ordersService.createOrder(userId)));
    }
}
