
package ru.javalab.mongodb_hateoas.model;

import lombok.*;
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