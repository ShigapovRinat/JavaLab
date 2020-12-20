package ru.javalab.mongodb.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "cpu")
public class Cpu {

    private String _id;
    private String title;
    private Integer price;
    private String producer;
}
