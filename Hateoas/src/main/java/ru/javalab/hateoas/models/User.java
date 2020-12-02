package ru.javalab.hateoas.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "\"user\"")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(name = "name",nullable = false)
    private String name;

    private String hashPassword;

    private boolean isConfirmed;

    @OneToOne(mappedBy = "user")
    @JoinColumn(name = "basket_id")
    private Basket basket;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToMany(mappedBy = "fromId")
    private List<Message> messages;

}