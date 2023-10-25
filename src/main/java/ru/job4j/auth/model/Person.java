package ru.job4j.auth.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"login"}))
public class Person implements FieldsCheckable {
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

    @Override
    public void checkFields() throws IllegalArgumentException {
        if ((this.login == null || "".equals(this.login)
                || this.password == null || "".equals(this.password))) {
            throw new IllegalArgumentException("login or password is empty");
        }
    }
}