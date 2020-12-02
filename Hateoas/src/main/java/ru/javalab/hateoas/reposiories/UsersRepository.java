package ru.javalab.hateoas.reposiories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.javalab.hateoas.models.Order;
import ru.javalab.hateoas.models.User;

import javax.transaction.Transactional;
import java.util.List;

@RepositoryRestResource
public interface UsersRepository extends CrudRepository<User, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update User user set user.isConfirmed = true where user.id =:userId")
    void confirmed(@Param("userId") Long userId);

//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query("update User user set user.order =:ord where user.id =:userId")
//    void addOrder(@Param("userId") Long userId, @Param("orders") Order ord);
}
