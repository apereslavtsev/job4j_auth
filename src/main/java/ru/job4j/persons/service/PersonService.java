package ru.job4j.persons.service;

import ru.job4j.persons.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    List<Person> findAll();

    Optional<Person> save(Person person);

    Optional<Person> findById(int id);

    boolean update(Person person);

    boolean deleteById(int id);
}
