package ru.job4j.persons.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.persons.model.Person;
import ru.job4j.persons.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimplePersonService implements PersonService {

    private final PersonRepository personRepository;

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Optional<Person> save(Person person) {
        Optional<Person> rsl = Optional.empty();
        try {
           rsl = Optional.of(personRepository.save(person));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    @Override
    public Optional<Person> findById(int id) {
        return personRepository.findById(id);
    }

    @Override
    public boolean update(Person person) {
        boolean updated = false;
        try {
            personRepository.save(person);
            updated = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return updated;
    }

    @Override
    public boolean deleteById(int id) {
        boolean deleted = false;
        try {
            personRepository.deleteById(id);
            deleted = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deleted;
    }

}
