package ru.javalab.hateoas.reposiories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.javalab.hateoas.models.Order;
import ru.javalab.hateoas.models.User;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "order", path = "order")
public interface OrdersRepository extends CrudRepository<Order, Long> {

    @RestResource(path = "byUser", rel = "user")
    List<Order> findAllByUser(User user);
}
