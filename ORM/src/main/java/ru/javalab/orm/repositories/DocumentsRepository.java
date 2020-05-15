package ru.javalab.orm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.javalab.orm.models.Document;

@Repository
public interface DocumentsRepository extends JpaRepository<Document, Long> {
}
