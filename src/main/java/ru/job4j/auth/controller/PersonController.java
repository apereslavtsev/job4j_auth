package ru.job4j.auth.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.auth.model.Operation;
import ru.job4j.auth.model.Person;
import ru.job4j.auth.model.PersonDTO;
import ru.job4j.auth.service.PersonService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/person")
@AllArgsConstructor
@Validated
public class PersonController {
    private final PersonService persons;
    private final PasswordEncoder encoder;
    private final ObjectMapper objectMapper;

    @GetMapping("/")
    public List<Person> findAll() {
        return this.persons.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable @Valid @Positive int id) {
        return this.persons.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Person with id=" + Integer.toString(id) + " not found"));
    }

    @PostMapping("/")
    @Validated(Operation.OnCreate.class)
    public ResponseEntity<Person> create(@RequestBody @Valid @NotNull Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        return this.persons.save(person)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(person)
                );
    }

    @PutMapping("/")
    @Validated(Operation.OnUpdate.class)
    public ResponseEntity<Void> update(@RequestBody @Valid @NotNull Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        boolean updated = persons.update(person);
        if (updated) {
            return ResponseEntity.ok().build();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Valid @Positive int id) {
        boolean deleted = persons.deleteById(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person is not deleted");
    }


    @PatchMapping("/")
    @Validated(Operation.OnUpdate.class)
    public ResponseEntity<Person> patch(@RequestBody @Valid @NotNull PersonDTO newPerson) {
        Optional<Person> currentPersonOpt = persons.findById(newPerson.getId());
        if (currentPersonOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Person with id=" + Integer.toString(newPerson.getId()) + " not found");
        }
        Person currentPerson = currentPersonOpt.get();
        currentPerson.setLogin(newPerson.getLogin());

        return persons.save(currentPerson)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(currentPerson));
    }

}