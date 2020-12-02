package ru.javalab.hateoas.reposiories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.javalab.hateoas.models.Basket;
import ru.javalab.hateoas.models.User;

import javax.transaction.Transactional;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "basket", path = "basket")
public interface BasketsRepository extends CrudRepository<Basket, Long> {

    @RestResource(path = "byUser", rel = "user")
    Optional<Basket> findByUser(User user);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Basket basket set basket.user =:user where basket.id =:basketId")
    void addUser(@Param("basketId") Long basketId, @Param("user") User user);
}
