package ru.job4j.auth.model;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"login"}))
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive(message = "Id must be non null", groups = {
            Operation.OnUpdate.class
    })
    private int id;

    @NotEmpty(message = "Login must be non null", groups = {
            Operation.OnUpdate.class, Operation.OnCreate.class
    })
    private String login;

    @NotEmpty(message = "Password must be non null", groups = {
            Operation.OnUpdate.class, Operation.OnCreate.class,
    })
    private String password;

    @Override
    public String toString() {
        return "Person{"
                + "id=" + id
                + ", login='" + login + '\''
                + '}';
    }

}