package ru.job4j.auth.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.job4j.auth.model.Person;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

public interface PersonService extends UserDetailsService {

    List<Person> findAll();

    Optional<Person> save(Person person);

    Optional<Person> findById(int id);

    boolean update(Person person);

    boolean deleteById(int id);

    Optional<Person> findByLogin(String login);

    Optional<Person> patch(Person person) throws InvocationTargetException, IllegalAccessException;

}
