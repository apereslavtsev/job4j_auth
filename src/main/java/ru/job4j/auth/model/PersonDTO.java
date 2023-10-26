package ru.job4j.auth.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Data
public class PersonDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive(message = "Id must be non null", groups = {
            Operation.OnUpdate.class
    })
    private int id;

    @NotEmpty(message = "Login must be non null", groups = {
            Operation.OnUpdate.class
    })
    private String login;
}
