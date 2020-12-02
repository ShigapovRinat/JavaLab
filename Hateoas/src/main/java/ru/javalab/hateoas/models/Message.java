package ru.javalab.hateoas.models;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne
    @JoinColumn(name = "from_id")
    private User fromId;

}