package kz.job4j.rest.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Data
@Accessors(chain = true)
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String login;
    private String password;
}