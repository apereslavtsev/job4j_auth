package ru.job4j.persons.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"login"}))
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String login;
    private String password;

    @Override
    public String toString() {
        return "Person{"
                + "id=" + id
                + ", login='" + login + '\''
                + '}';
    }
}