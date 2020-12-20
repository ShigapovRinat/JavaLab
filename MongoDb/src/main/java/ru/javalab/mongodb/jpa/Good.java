package ru.javalab.mongodb.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "good")
public class Good {
    @Id
    private String _id;
    private String title;
    private Integer price;
    private String producer;

    @DBRef
    private Cpu cpu;
}