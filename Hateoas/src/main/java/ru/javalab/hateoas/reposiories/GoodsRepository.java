package ru.javalab.hateoas.reposiories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.javalab.hateoas.models.Good;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "good", path = "good")
public interface GoodsRepository extends CrudRepository<Good, Long> {

    @RestResource(path = "byCategory", rel = "category")
    List<Good> findAllByCategory(String category);
}
