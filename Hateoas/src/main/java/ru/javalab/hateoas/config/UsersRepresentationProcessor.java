package ru.javalab.hateoas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import ru.javalab.hateoas.controllers.UsersController;
import ru.javalab.hateoas.models.User;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsersRepresentationProcessor implements RepresentationModelProcessor<EntityModel<User>> {

    @Autowired
    private RepositoryEntityLinks links;

    @Override
    public EntityModel<User> process(EntityModel<User> model) {
        User user = model.getContent();

        if (user!= null && user.isConfirmed()) {
            model.add(linkTo(methodOn(UsersController.class)
                    .createOrder(user.getId())).withRel("order-create"));
        }

        if (user!= null && !user.isConfirmed()) {
            model.add(links.linkToItemResource(User.class, user.getId()).
                    withRel("confirmed"));
        }

        return model;
    }
}
