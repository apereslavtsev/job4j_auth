package ru.job4j.persons.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.job4j.persons.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService extends UserDetailsService {

    List<Person> findAll();

    Optional<Person> save(Person person);

    Optional<Person> findById(int id);

    boolean update(Person person);

    boolean deleteById(int id);

    Optional<Person> findByLogin(String login);

}
