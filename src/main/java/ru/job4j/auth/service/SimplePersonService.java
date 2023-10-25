package ru.job4j.auth.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.auth.model.Person;
import ru.job4j.auth.repository.PersonRepository;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import static java.util.Collections.emptyList;

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

    @Override
    public Optional<Person> findByLogin(String login) {
       return personRepository.findByLogin(login);
    }

    @Override
    public Optional<Person> patch(Person person) throws InvocationTargetException, IllegalAccessException {
        Optional<Person> currentOpt = personRepository.findById(person.getId());
        if (currentOpt.isEmpty()) {
            return currentOpt;
        }
        Person current = currentOpt.get();
        var methods = current.getClass().getDeclaredMethods();
        var namePerMethod = new HashMap<String, Method>();
        for (var method: methods) {
            var name = method.getName();
            if (name.startsWith("get") || name.startsWith("set")) {
                namePerMethod.put(name, method);
            }
        }
        for (var name : namePerMethod.keySet()) {
            if (name.startsWith("get")) {
                var getMethod = namePerMethod.get(name);
                var setMethod = namePerMethod.get(name.replace("get", "set"));
                if (setMethod == null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Impossible invoke set method from object : " + current + ", Check set and get pairs.");
                }
                var newValue = getMethod.invoke(person);
                if (newValue != null) {
                    setMethod.invoke(current, newValue);
                }
            }
        }
        personRepository.save(current);
        return Optional.of(current);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return personRepository.findByLogin(username)
                .map(person ->  new User(person.getLogin(), person.getPassword(), emptyList()))
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
