package ru.javalab.mongodb_hateoas.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

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
