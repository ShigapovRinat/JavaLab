package ru.javalab.hateoas.reposiories;

import org.springframework.data.repository.CrudRepository;
import ru.javalab.hateoas.models.Company;

public interface CompaniesRepository extends CrudRepository<Company, Long> {
}
